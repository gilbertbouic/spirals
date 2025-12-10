# Hand-Tracked Digital Spirals for Android

An Android application that uses hand tracking to manipulate 5 different digital spirals on the screen via the front-facing camera.

## Features

- **Real-time Hand Tracking**: Uses MediaPipe Hands solution for accurate hand landmark detection
- **5 Interactive Spirals**: Each finger tip (thumb, index, middle, ring, pinky) controls a separate spiral
- **Visual Feedback**: Each spiral has a unique color (red, green, blue, yellow, magenta)
- **Camera Preview**: Live camera feed with overlay of spirals

## Prerequisites

- Android Studio Arctic Fox or later
- Android device or emulator with API level 24 (Android 7.0) or higher
- Device with a front-facing camera
- Internet connection to download dependencies

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/gilbertbouic/spirals.git
cd spirals
```

### 2. Download MediaPipe Model

**IMPORTANT**: You must manually download the hand tracking model file:

1. Download from: https://storage.googleapis.com/mediapipe-models/hand_landmarker/hand_landmarker/float16/1/hand_landmarker.task
2. Place the file in: `app/src/main/assets/hand_landmarker.task`

The file should be approximately 11-12 MB.

### 3. Open in Android Studio

1. Open Android Studio
2. Select "Open an existing project"
3. Navigate to the cloned repository folder
4. Wait for Gradle sync to complete

### 4. Build and Run

1. Connect an Android device or start an emulator
2. Click the "Run" button (green triangle) in Android Studio
3. Select your device
4. Grant camera permission when prompted

## How to Use

1. Launch the app on your Android device
2. Grant camera permission when prompted
3. Show your hand to the front-facing camera
4. Each of your five finger tips will control one spiral:
   - **Thumb** → Red spiral
   - **Index finger** → Green spiral
   - **Middle finger** → Blue spiral
   - **Ring finger** → Yellow spiral
   - **Pinky** → Magenta spiral
5. Move your hand to see the spirals follow your finger tips
6. The spirals will continuously rotate for visual effect

## Technical Details

### Architecture

- **MainActivity**: Handles camera permissions, CameraX setup, and MediaPipe hand tracking
- **SpiralsView**: Custom View that renders the 5 spirals and updates their positions
- **Hand Tracking**: MediaPipe Hands solution processes camera frames in real-time

### Dependencies

- AndroidX Camera (CameraX) for camera functionality
- MediaPipe Tasks Vision for hand landmark detection
- Material Design components for UI

### Permissions

- `CAMERA`: Required for accessing the device camera

## Troubleshooting

**Hand tracking not working:**
- Ensure the `hand_landmarker.task` file is in `app/src/main/assets/`
- Check that camera permission has been granted
- Make sure you're using the front-facing camera
- Ensure good lighting conditions

**Build errors:**
- Make sure you have the latest Android SDK installed
- Try "Clean Project" and "Rebuild Project" in Android Studio
- Check that Gradle sync completed successfully

## License

See LICENSE file for details.
