# Build Instructions

## Prerequisites

1. **Android Studio**: Download and install from https://developer.android.com/studio
2. **Internet Connection**: Required for downloading dependencies
3. **Android SDK**: Install via Android Studio (API level 24 or higher)

## Building the Project

### Method 1: Using Android Studio (Recommended)

1. Open Android Studio
2. Select "File" → "Open"
3. Navigate to the project directory and select it
4. Wait for Gradle sync to complete (first time may take several minutes)
5. Download the MediaPipe model file (see below)
6. Click "Build" → "Make Project" or press Ctrl+F9 (Cmd+F9 on Mac)
7. If successful, click "Run" → "Run 'app'" or press Shift+F10

### Method 2: Using Command Line

1. Ensure you have JDK 8 or higher installed
2. Download the MediaPipe model file (see below)
3. From the project root directory, run:
   ```bash
   ./gradlew build
   ```
4. To install on a connected device:
   ```bash
   ./gradlew installDebug
   ```

## Required Model File

**CRITICAL**: The app requires the MediaPipe hand tracking model:

1. Download from: 
   ```
   https://storage.googleapis.com/mediapipe-models/hand_landmarker/hand_landmarker/float16/1/hand_landmarker.task
   ```

2. Place it in:
   ```
   app/src/main/assets/hand_landmarker.task
   ```

3. Verify the file is approximately 11-12 MB

## Running on Device/Emulator

1. Enable Developer Options on your Android device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings → Developer Options
   - Enable "USB Debugging"

2. Connect your device via USB and authorize the computer

3. In Android Studio, select your device from the device dropdown

4. Click the Run button (green triangle)

5. Grant camera permission when prompted

## Troubleshooting

### Gradle Sync Failed
- Check your internet connection
- Try "File" → "Invalidate Caches / Restart"
- Ensure you have the correct JDK version

### Build Failed
- Clean the project: "Build" → "Clean Project"
- Rebuild: "Build" → "Rebuild Project"
- Check that all dependencies are downloaded

### App Crashes on Launch
- Verify the `hand_landmarker.task` file exists in `app/src/main/assets/`
- Check that the file is not corrupted (should be ~11-12 MB)
- Ensure camera permission is granted

### Hand Tracking Not Working
- Ensure good lighting
- Point the front camera at your hand
- Keep hand within camera frame
- Check camera permission is granted
