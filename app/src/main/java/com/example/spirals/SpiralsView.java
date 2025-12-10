package com.example.spirals;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SpiralsView extends View {
    private Paint paint;
    private List<Spiral> spirals;
    private int viewWidth;
    private int viewHeight;

    public SpiralsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        paint.setAntiAlias(true);
        
        spirals = new ArrayList<>();
        // Initialize 5 spirals with different colors
        int[] colors = {
            Color.rgb(255, 100, 100),  // Red
            Color.rgb(100, 255, 100),  // Green
            Color.rgb(100, 100, 255),  // Blue
            Color.rgb(255, 255, 100),  // Yellow
            Color.rgb(255, 100, 255)   // Magenta
        };
        
        for (int i = 0; i < 5; i++) {
            spirals.add(new Spiral(colors[i]));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        
        // Position spirals in a grid initially
        if (spirals != null && spirals.size() == 5) {
            spirals.get(0).setPosition(w * 0.25f, h * 0.33f);
            spirals.get(1).setPosition(w * 0.75f, h * 0.33f);
            spirals.get(2).setPosition(w * 0.5f, h * 0.5f);
            spirals.get(3).setPosition(w * 0.25f, h * 0.67f);
            spirals.get(4).setPosition(w * 0.75f, h * 0.67f);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        for (Spiral spiral : spirals) {
            spiral.draw(canvas, paint);
        }
    }

    // Update spiral positions based on hand landmarks
    public void updateSpiralPositions(List<float[]> fingerTips) {
        if (fingerTips == null || fingerTips.isEmpty()) {
            return;
        }
        
        // Map each finger tip to a spiral (thumb, index, middle, ring, pinky)
        for (int i = 0; i < Math.min(fingerTips.size(), spirals.size()); i++) {
            float[] tip = fingerTips.get(i);
            if (tip != null && tip.length >= 2) {
                // Convert normalized coordinates to screen coordinates
                float x = tip[0] * viewWidth;
                float y = tip[1] * viewHeight;
                spirals.get(i).setPosition(x, y);
            }
        }
        
        invalidate(); // Trigger redraw
    }

    // Spiral class represents a single spiral
    static class Spiral {
        private float centerX;
        private float centerY;
        private int color;
        private float rotation = 0f;
        private float scale = 1f;
        
        public Spiral(int color) {
            this.color = color;
        }
        
        public void setPosition(float x, float y) {
            this.centerX = x;
            this.centerY = y;
            // Add some animation by slowly rotating
            rotation += 0.5f;
        }
        
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(color);
            
            Path path = new Path();
            float angleStep = 0.1f;
            float radiusStep = 0.5f;
            float maxAngle = 8 * (float)Math.PI;
            
            boolean first = true;
            for (float angle = 0; angle < maxAngle; angle += angleStep) {
                float radius = radiusStep * angle * scale;
                float x = centerX + radius * (float)Math.cos(angle + rotation * 0.1f);
                float y = centerY + radius * (float)Math.sin(angle + rotation * 0.1f);
                
                if (first) {
                    path.moveTo(x, y);
                    first = false;
                } else {
                    path.lineTo(x, y);
                }
            }
            
            canvas.drawPath(path, paint);
        }
    }
}
