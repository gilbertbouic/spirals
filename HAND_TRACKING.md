# Hand Tracking Technical Details

## MediaPipe Hand Landmarker

The app uses Google's MediaPipe Hand Landmarker model to detect and track hands in real-time.

### Hand Landmarks

MediaPipe detects 21 3D landmarks on each hand:

```
        8   12  16  20
        |   |   |   |
    4   |   |   |   |
    |   |   |   |   |
    |   |   |   |   |
    0---5---9--13--17
        |
        |
        1
        |
        2
        |
        3
```

### Landmark Indices

- **Wrist**: 0
- **Thumb**: 1, 2, 3, 4 (tip)
- **Index finger**: 5, 6, 7, 8 (tip)
- **Middle finger**: 9, 10, 11, 12 (tip)
- **Ring finger**: 13, 14, 15, 16 (tip)
- **Pinky**: 17, 18, 19, 20 (tip)

### Finger Tip to Spiral Mapping

This app uses the 5 finger tips to control 5 spirals:

| Finger | Landmark Index | Spiral Color | Spiral Index |
|--------|----------------|--------------|--------------|
| Thumb  | 4              | Red          | 0            |
| Index  | 8              | Green        | 1            |
| Middle | 12             | Blue         | 2            |
| Ring   | 16             | Yellow       | 3            |
| Pinky  | 20             | Magenta      | 4            |

### Coordinate System

- **x**: Horizontal position (0.0 = left, 1.0 = right)
- **y**: Vertical position (0.0 = top, 1.0 = bottom)
- **z**: Depth (relative to wrist, smaller values are closer)

The app uses only x and y coordinates for 2D positioning.

### Detection Parameters

Configured in `MainActivity.java`:

```java
.setNumHands(2)                          // Detect up to 2 hands
.setMinHandDetectionConfidence(0.5f)     // 50% confidence threshold
.setMinHandPresenceConfidence(0.5f)      // 50% presence threshold
.setMinTrackingConfidence(0.5f)          // 50% tracking threshold
```

### Performance

- **Running Mode**: LIVE_STREAM for real-time processing
- **Frame Processing**: Asynchronous with latest frame strategy
- **Typical Latency**: < 50ms on modern devices
- **FPS**: 30+ frames per second

### Model Information

- **File**: hand_landmarker.task
- **Size**: ~11-12 MB
- **Format**: TFLite task file
- **Version**: Float16 precision
- **Source**: Google MediaPipe

### References

- MediaPipe Hands: https://developers.google.com/mediapipe/solutions/vision/hand_landmarker
- Hand Landmarks Guide: https://developers.google.com/mediapipe/solutions/vision/hand_landmarker/index#models
