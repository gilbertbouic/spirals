# App Architecture and Flow

## Component Overview

```
┌─────────────────────────────────────────────┐
│           MainActivity                       │
│  ┌───────────────────────────────────────┐ │
│  │  Camera Preview (PreviewView)         │ │
│  │  - Front-facing camera feed           │ │
│  └───────────────────────────────────────┘ │
│  ┌───────────────────────────────────────┐ │
│  │  Spirals Overlay (SpiralsView)        │ │
│  │  - 5 colored spirals                  │ │
│  │  - Transparent background             │ │
│  └───────────────────────────────────────┘ │
│  ┌───────────────────────────────────────┐ │
│  │  Status Text                          │ │
│  │  - Shows hand tracking status         │ │
│  └───────────────────────────────────────┘ │
└─────────────────────────────────────────────┘
```

## Hand Tracking Flow

```
1. Camera Frame Captured
   ↓
2. Convert to Bitmap
   ↓
3. Send to MediaPipe Hand Landmarker
   ↓
4. Detect Hand Landmarks (21 points per hand)
   ↓
5. Extract Finger Tip Positions
   - Landmark 4: Thumb tip
   - Landmark 8: Index finger tip
   - Landmark 12: Middle finger tip
   - Landmark 16: Ring finger tip
   - Landmark 20: Pinky tip
   ↓
6. Update Spiral Positions
   - Thumb → Red Spiral (0)
   - Index → Green Spiral (1)
   - Middle → Blue Spiral (2)
   - Ring → Yellow Spiral (3)
   - Pinky → Magenta Spiral (4)
   ↓
7. Redraw SpiralsView
```

## Spiral Rendering

Each spiral is drawn using a mathematical formula:
- Archimedean spiral: r = a + b*θ
- Continuously rotating for animation effect
- Position controlled by corresponding finger tip

## Coordinate System

- MediaPipe returns normalized coordinates (0.0 to 1.0)
- SpiralsView converts to screen coordinates
- Front camera image is mirrored for natural interaction

## File Structure

```
spirals/
├── app/
│   ├── build.gradle              # App dependencies
│   ├── src/
│   │   └── main/
│   │       ├── AndroidManifest.xml       # Permissions & app config
│   │       ├── assets/
│   │       │   ├── hand_landmarker.task  # MediaPipe model (download required)
│   │       │   └── README.md             # Model download instructions
│   │       ├── java/com/example/spirals/
│   │       │   ├── MainActivity.java     # Main activity with camera & hand tracking
│   │       │   └── SpiralsView.java      # Custom view for rendering spirals
│   │       └── res/
│   │           └── layout/
│   │               └── activity_main.xml # Main layout
├── build.gradle              # Project-level build config
├── settings.gradle          # Gradle settings
├── gradle.properties        # Gradle properties
├── README.md               # User documentation
└── BUILD.md                # Build instructions
```

## Key Features

### 1. Real-time Hand Tracking
- Uses MediaPipe Hands (Google's ML solution)
- Processes camera frames at high FPS
- Detects up to 2 hands simultaneously
- 21 landmark points per hand

### 2. Multi-Spiral Manipulation
- 5 independent spirals
- Each controlled by a different finger
- Unique colors for easy identification
- Smooth animation and rotation

### 3. Camera Integration
- CameraX for modern camera API
- Front camera for selfie-mode
- Live preview with overlay
- Efficient image processing

### 4. Responsive UI
- Real-time status updates
- Permission handling
- Error messages
- Full-screen experience

## Performance Considerations

- Image processing runs on separate executor thread
- Only latest frame processed (STRATEGY_KEEP_ONLY_LATEST)
- Efficient bitmap conversion
- Minimal UI updates (only when hand detected)

## Permissions

Required permissions in AndroidManifest.xml:
- CAMERA: Access device camera for hand tracking
