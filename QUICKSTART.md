# Quick Start Guide

Get up and running with the Spirals app in 5 minutes!

## Prerequisites

- ✅ Android Studio installed
- ✅ Android device or emulator (Android 7.0+)
- ✅ Internet connection

## 5-Minute Setup

### Step 1: Download the Model File (2 minutes)

1. Open your browser
2. Go to: https://storage.googleapis.com/mediapipe-models/hand_landmarker/hand_landmarker/float16/1/hand_landmarker.task
3. Download the file (it's about 11-12 MB)
4. Save it somewhere you can find it

### Step 2: Open the Project (1 minute)

1. Open Android Studio
2. Click "Open" or "File → Open"
3. Navigate to the `spirals` folder
4. Click "OK"
5. Wait for Gradle sync to complete (first time will download dependencies)

### Step 3: Add the Model File (1 minute)

1. In Android Studio, find `app/src/main/assets/` in the Project view
2. Drag and drop the `hand_landmarker.task` file into the `assets` folder
3. Click "OK" when asked to copy the file

### Step 4: Build and Run (1 minute)

1. Connect your Android device via USB (or start an emulator)
2. Enable USB debugging on your device if needed
3. Click the green "Run" button (▶️) in Android Studio
4. Select your device
5. Wait for the app to install

### Step 5: Use the App!

1. Grant camera permission when prompted
2. Show your hand to the camera
3. Move your fingers and watch the spirals follow!

## Troubleshooting

### "Model file not found" error
→ Make sure `hand_landmarker.task` is in `app/src/main/assets/`

### "Permission denied" error
→ Grant camera permission in app settings

### Gradle sync failed
→ Check your internet connection and try "File → Sync Project with Gradle Files"

### Hand tracking not working
→ Ensure good lighting and keep your hand in frame

## What Each Finger Does

👍 **Thumb** → Red spiral
👆 **Index** → Green spiral
🖕 **Middle** → Blue spiral
💍 **Ring** → Yellow spiral
🤙 **Pinky** → Magenta spiral

## Tips for Best Experience

- Use good lighting
- Keep your hand within the camera frame
- Try different hand gestures
- Move your hand slowly at first
- Experiment with different finger combinations

## Need More Help?

- Check the FAQ.md for common questions
- Read BUILD.md for detailed build instructions
- Review ARCHITECTURE.md to understand how it works

## That's It!

You're ready to manipulate digital spirals with your hand! 🎨✨
