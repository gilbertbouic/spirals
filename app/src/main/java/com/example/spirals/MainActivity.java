package com.example.spirals;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mediapipe.framework.image.BitmapImageBuilder;
import com.google.mediapipe.framework.image.MPImage;
import com.google.mediapipe.tasks.core.BaseOptions;
import com.google.mediapipe.tasks.vision.core.RunningMode;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SpiralsMainActivity";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    
    private PreviewView previewView;
    private SpiralsView spiralsView;
    private TextView statusText;
    
    private ExecutorService cameraExecutor;
    private HandLandmarker handLandmarker;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        previewView = findViewById(R.id.previewView);
        spiralsView = findViewById(R.id.spiralsView);
        statusText = findViewById(R.id.statusText);
        
        cameraExecutor = Executors.newSingleThreadExecutor();
        
        if (checkCameraPermission()) {
            setupHandLandmarker();
            startCamera();
        } else {
            requestCameraPermission();
        }
    }
    
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }
    
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                          @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupHandLandmarker();
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    
    private void setupHandLandmarker() {
        try {
            BaseOptions baseOptions = BaseOptions.builder()
                    .setModelAssetPath("hand_landmarker.task")
                    .build();
            
            HandLandmarker.HandLandmarkerOptions options = HandLandmarker.HandLandmarkerOptions.builder()
                    .setBaseOptions(baseOptions)
                    .setRunningMode(RunningMode.LIVE_STREAM)
                    .setNumHands(2)
                    .setMinHandDetectionConfidence(0.5f)
                    .setMinHandPresenceConfidence(0.5f)
                    .setMinTrackingConfidence(0.5f)
                    .setResultListener(this::onHandLandmarkerResult)
                    .setErrorListener((message, e) -> {
                        Log.e(TAG, "Hand landmarker error: " + message);
                    })
                    .build();
            
            handLandmarker = HandLandmarker.createFromOptions(this, options);
            
            runOnUiThread(() -> statusText.setText("Hand tracking ready"));
            
        } catch (Exception e) {
            Log.e(TAG, "Error setting up hand landmarker: " + e.getMessage());
            runOnUiThread(() -> statusText.setText("Hand tracking setup failed"));
        }
    }
    
    private void onHandLandmarkerResult(HandLandmarkerResult result, MPImage input) {
        if (result == null || result.landmarks().isEmpty()) {
            return;
        }
        
        // Extract finger tip positions from the first detected hand
        List<float[]> fingerTips = new ArrayList<>();
        
        // MediaPipe hand landmark indices for finger tips:
        // 4: Thumb tip, 8: Index tip, 12: Middle tip, 16: Ring tip, 20: Pinky tip
        int[] tipIndices = {4, 8, 12, 16, 20};
        
        if (!result.landmarks().isEmpty()) {
            var handLandmarks = result.landmarks().get(0);
            
            for (int tipIndex : tipIndices) {
                if (tipIndex < handLandmarks.size()) {
                    var landmark = handLandmarks.get(tipIndex);
                    // Note: x and y are already normalized (0-1)
                    fingerTips.add(new float[]{landmark.x(), landmark.y()});
                }
            }
        }
        
        // Update spirals on UI thread
        runOnUiThread(() -> {
            spiralsView.updateSpiralPositions(fingerTips);
            int handCount = result.landmarks().size();
            statusText.setText(String.format("Tracking %d hand(s)", handCount));
        });
    }
    
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);
        
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraUseCases(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error starting camera: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));
    }
    
    private void bindCameraUseCases(ProcessCameraProvider cameraProvider) {
        // Preview
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        
        // Image analysis for hand tracking
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build();
        
        imageAnalysis.setAnalyzer(cameraExecutor, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy imageProxy) {
                processImageProxy(imageProxy);
            }
        });
        
        // Use front camera for selfie mode
        CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
        
        try {
            cameraProvider.unbindAll();
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
        } catch (Exception e) {
            Log.e(TAG, "Use case binding failed", e);
        }
    }
    
    private void processImageProxy(ImageProxy imageProxy) {
        if (handLandmarker == null) {
            imageProxy.close();
            return;
        }
        
        try {
            Bitmap bitmap = imageProxyToBitmap(imageProxy);
            if (bitmap != null) {
                MPImage mpImage = new BitmapImageBuilder(bitmap).build();
                long frameTime = System.currentTimeMillis();
                handLandmarker.detectAsync(mpImage, frameTime);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing image: " + e.getMessage());
        } finally {
            imageProxy.close();
        }
    }
    
    private Bitmap imageProxyToBitmap(ImageProxy imageProxy) {
        ImageProxy.PlaneProxy[] planes = imageProxy.getPlanes();
        if (planes.length == 0) return null;
        
        ImageProxy.PlaneProxy plane = planes[0];
        int width = imageProxy.getWidth();
        int height = imageProxy.getHeight();
        
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(plane.getBuffer());
        
        // Mirror the image for front camera
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
        if (handLandmarker != null) {
            handLandmarker.close();
        }
    }
}
