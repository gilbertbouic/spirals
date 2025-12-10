# Frequently Asked Questions (FAQ)

## General Questions

### Q: What is this app?
**A:** This is an Android application that uses your device's front camera to track your hand in real-time. Each of your five finger tips controls a separate colored spiral on the screen, creating an interactive visual experience.

### Q: Do I need an internet connection to use the app?
**A:** After the initial setup and model download, the app works completely offline. Hand tracking is performed on-device using the MediaPipe model.

### Q: Does the app work on tablets?
**A:** Yes! The app works on any Android device with a front-facing camera running Android 7.0 (API level 24) or higher.

## Setup Questions

### Q: Where do I download the hand tracking model?
**A:** Download from: https://storage.googleapis.com/mediapipe-models/hand_landmarker/hand_landmarker/float16/1/hand_landmarker.task

Place it in: `app/src/main/assets/hand_landmarker.task`

### Q: The model file seems large. Is this normal?
**A:** Yes, the model file is approximately 11-12 MB. This is expected for a machine learning model that performs real-time hand tracking.

### Q: Can I use a different MediaPipe model?
**A:** The app is configured for the specific hand_landmarker.task model. Using a different model may require code changes.

### Q: What if I don't have Android Studio?
**A:** You can build from the command line using Gradle. See BUILD.md for instructions. However, Android Studio is recommended for easier setup.

## Usage Questions

### Q: Why doesn't my hand get detected?
**A:** Common reasons:
- Poor lighting conditions
- Hand too far from camera
- Hand partially outside camera frame
- Camera permission not granted
- Model file not installed correctly

### Q: Can I use both hands?
**A:** The app is configured to detect up to 2 hands, but only uses the first detected hand to control the spirals.

### Q: Why do spirals keep moving even when I'm not moving my hand?
**A:** The spirals continuously rotate for visual effect. This is intentional and creates the animated appearance.

### Q: Can I change the spiral colors?
**A:** Yes, you can modify the colors in `SpiralsView.java` in the `init()` method where the color array is defined.

### Q: How many spirals can I have?
**A:** The app is designed for 5 spirals (one per finger). You can modify this by changing the SpiralsView code, but you'll need to adjust the hand tracking logic accordingly.

## Technical Questions

### Q: Which camera does the app use?
**A:** The app uses the front-facing camera (selfie camera) in portrait orientation for a natural selfie-like interaction.

### Q: How fast is the hand tracking?
**A:** On modern devices, the app processes 30+ frames per second with latency typically under 50ms.

### Q: Does the app use a lot of battery?
**A:** Camera and ML processing are power-intensive. It's normal for the app to consume more battery than typical apps. Close the app when not in use.

### Q: Does the app collect or send any data?
**A:** No. All processing happens on-device. No images or hand tracking data are transmitted or stored.

### Q: What Android version do I need?
**A:** Minimum: Android 7.0 (API level 24)
Recommended: Android 10.0 (API level 29) or higher

### Q: Can I use the rear camera instead?
**A:** The app is configured for the front camera, but you can change this in MainActivity.java by modifying the CameraSelector from `DEFAULT_FRONT_CAMERA` to `DEFAULT_BACK_CAMERA`.

## Build & Development Questions

### Q: Why does the build fail?
**A:** Common causes:
- Internet connection required for first build (to download dependencies)
- Missing Android SDK
- Wrong JDK version (need JDK 8+)
- Model file missing

### Q: Can I customize the spirals?
**A:** Yes! The spiral drawing code is in SpiralsView.java. You can modify:
- Number of turns (maxAngle)
- Spiral tightness (radiusStep)
- Line thickness (strokeWidth)
- Animation speed (rotation increment)
- Colors

### Q: How do I change the spiral shape?
**A:** In `SpiralsView.java`, modify the `draw()` method in the Spiral class. The current formula creates an Archimedean spiral, but you can implement other spiral types.

### Q: Can I add more than 5 spirals?
**A:** Yes, but you'll need to decide which hand landmarks to use since there are only 5 finger tips. You could use other landmarks like finger joints or knuckles.

### Q: How do I debug hand tracking issues?
**A:** Enable logging by checking the Android Logcat for messages tagged "SpiralsMainActivity". The app logs hand tracking errors and detection counts.

## Performance Questions

### Q: Why is the app laggy?
**A:** Possible causes:
- Older/slower device
- Many background apps running
- Insufficient lighting (camera needs to work harder)
- Try closing other apps

### Q: Does screen resolution affect performance?
**A:** Yes, higher resolution screens require more rendering work. The spirals are drawn in real-time for each frame.

### Q: Can I reduce the detection quality to improve performance?
**A:** Yes, in MainActivity.java, you can adjust:
- Increase confidence thresholds (less sensitive, faster)
- Reduce image analysis resolution
- Decrease spiral complexity

## Privacy & Security Questions

### Q: Does the app store camera images?
**A:** No. Images are processed in memory only and immediately discarded after hand tracking.

### Q: Can someone else see my camera feed?
**A:** No. The app runs entirely on your device with no network communication.

### Q: What permissions does the app need?
**A:** Only CAMERA permission. The app requests this on first launch.

## Troubleshooting

### Q: App crashes on startup
**A:** Check:
1. Camera permission granted
2. Model file exists and is correct size (~11-12 MB)
3. Device meets minimum requirements (Android 7.0+)
4. Try reinstalling the app

### Q: Status text says "Hand tracking setup failed"
**A:** The model file is likely missing or corrupted. Verify:
- File exists at `app/src/main/assets/hand_landmarker.task`
- File size is approximately 11-12 MB
- Re-download the file if needed

### Q: Spirals don't move when I move my hand
**A:** Check:
- Status text shows "Tracking X hand(s)" when hand is visible
- Hand is within camera frame
- Good lighting
- Hand is not too close or too far from camera

## Support

For issues not covered here:
1. Check the documentation: README.md, BUILD.md, ARCHITECTURE.md
2. Review the code: The app is open source
3. Open an issue on GitHub with details about your problem
