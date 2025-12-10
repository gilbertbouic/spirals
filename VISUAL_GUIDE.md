# App Screenshots & Visual Guide

## App Interface

The app displays a full-screen camera preview with 5 colored spirals overlaid on top.

### Screen Layout

```
┌────────────────────────────────┐
│  ● Tracking X hand(s)          │ ← Status Text (top-left)
│                                │
│         Camera Preview         │
│                                │
│    🔴                          │ ← Red Spiral (Thumb)
│              🟢                │ ← Green Spiral (Index)
│                                │
│                   🔵           │ ← Blue Spiral (Middle)
│                                │
│    🟡                          │ ← Yellow Spiral (Ring)
│              🟣                │ ← Magenta Spiral (Pinky)
│                                │
│                                │
└────────────────────────────────┘
```

## Spiral Colors

Each finger controls a different colored spiral:

1. **Thumb** (Landmark 4) → 🔴 Red Spiral
2. **Index Finger** (Landmark 8) → 🟢 Green Spiral
3. **Middle Finger** (Landmark 12) → 🔵 Blue Spiral
4. **Ring Finger** (Landmark 16) → 🟡 Yellow Spiral
5. **Pinky** (Landmark 20) → 🟣 Magenta/Purple Spiral

## Usage Examples

### Example 1: Open Hand
```
Show your open hand to the camera:
- All 5 spirals will spread out following your finger tips
- Each spiral continuously rotates for visual effect
```

### Example 2: Fist
```
Make a fist:
- All spirals will converge near the center
- They will still be visible and rotating
```

### Example 3: Peace Sign
```
Show two fingers (index and middle):
- Two spirals (green and blue) will be at finger tips
- Other spirals will remain at last known positions
```

### Example 4: Wave
```
Wave your hand:
- All spirals will smoothly follow the motion
- Creates a trailing effect as spirals animate
```

## Visual Elements

### Spirals
- **Style**: Smooth, curved lines
- **Thickness**: 3px stroke width
- **Animation**: Continuous rotation
- **Pattern**: Archimedean spiral (evenly spaced)
- **Turns**: ~4 complete rotations per spiral

### Camera Preview
- **Source**: Front-facing camera
- **Orientation**: Portrait mode
- **Mirror**: Yes (selfie mode)
- **Full-screen**: Covers entire screen

### Status Text
- **Position**: Top-left corner
- **Background**: Semi-transparent black
- **Color**: White text
- **Content**: "Tracking X hand(s)" or "Initializing..."

## Color Codes

RGB values for each spiral:

| Spiral | Color   | RGB Value       | Hex Code |
|--------|---------|-----------------|----------|
| 0      | Red     | (255, 100, 100) | #FF6464  |
| 1      | Green   | (100, 255, 100) | #64FF64  |
| 2      | Blue    | (100, 100, 255) | #6464FF  |
| 3      | Yellow  | (255, 255, 100) | #FFFF64  |
| 4      | Magenta | (255, 100, 255) | #FF64FF  |

## Initial Positions

When the app first launches (before hand detection), spirals are positioned in a grid:

```
┌────────────────────────────┐
│                            │
│    🔴           🟢         │  ← Row 1 (33% down)
│                            │
│          🔵                │  ← Row 2 (50% down)
│                            │
│    🟡           🟣         │  ← Row 3 (67% down)
│                            │
└────────────────────────────┘
```

Positions:
- Red: (25%, 33%)
- Green: (75%, 33%)
- Blue: (50%, 50%)
- Yellow: (25%, 67%)
- Magenta: (75%, 67%)

## Interaction Flow

1. **Launch App** → Grant camera permission
2. **Show Hand** → Spirals move to finger tips
3. **Move Hand** → Spirals smoothly follow
4. **Multiple Hands** → Only first hand is tracked
5. **No Hand** → Spirals remain at last position
