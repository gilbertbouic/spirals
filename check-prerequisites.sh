#!/bin/bash

# Spirals App Prerequisites Check Script

echo "=================================="
echo "Spirals App Prerequisites Checker"
echo "=================================="
echo ""

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Track if all checks pass
ALL_CHECKS_PASSED=true

# Check 1: Java/JDK
echo "Checking Java/JDK installation..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓${NC} Java found: $JAVA_VERSION"
else
    echo -e "${RED}✗${NC} Java not found. Please install JDK 8 or higher."
    ALL_CHECKS_PASSED=false
fi
echo ""

# Check 2: Android SDK
echo "Checking Android SDK..."
if [ -n "$ANDROID_HOME" ]; then
    echo -e "${GREEN}✓${NC} ANDROID_HOME is set: $ANDROID_HOME"
else
    echo -e "${YELLOW}!${NC} ANDROID_HOME not set. This is required for command-line builds."
    echo "  Set it in your ~/.bashrc or ~/.zshrc:"
    echo "  export ANDROID_HOME=/path/to/Android/Sdk"
    ALL_CHECKS_PASSED=false
fi
echo ""

# Check 3: Gradle
echo "Checking Gradle..."
if [ -f "./gradlew" ]; then
    echo -e "${GREEN}✓${NC} Gradle wrapper found (./gradlew)"
else
    echo -e "${YELLOW}!${NC} Gradle wrapper not found. It will be generated on first build."
fi
echo ""

# Check 4: MediaPipe Model File
echo "Checking MediaPipe model file..."
MODEL_PATH="app/src/main/assets/hand_landmarker.task"
if [ -f "$MODEL_PATH" ]; then
    FILE_SIZE=$(du -h "$MODEL_PATH" | cut -f1)
    if [ $(stat -f%z "$MODEL_PATH" 2>/dev/null || stat -c%s "$MODEL_PATH" 2>/dev/null) -gt 1000000 ]; then
        echo -e "${GREEN}✓${NC} Model file found: $FILE_SIZE"
    else
        echo -e "${RED}✗${NC} Model file found but too small. Please re-download."
        echo "  Expected size: ~11-12 MB"
        ALL_CHECKS_PASSED=false
    fi
else
    echo -e "${RED}✗${NC} Model file not found at: $MODEL_PATH"
    echo "  Please download from:"
    echo "  https://storage.googleapis.com/mediapipe-models/hand_landmarker/hand_landmarker/float16/1/hand_landmarker.task"
    ALL_CHECKS_PASSED=false
fi
echo ""

# Check 5: Internet Connection (for first build)
echo "Checking internet connection..."
if ping -c 1 google.com &> /dev/null || ping -c 1 8.8.8.8 &> /dev/null; then
    echo -e "${GREEN}✓${NC} Internet connection available"
else
    echo -e "${YELLOW}!${NC} No internet connection detected."
    echo "  Internet is required for downloading dependencies on first build."
fi
echo ""

# Summary
echo "=================================="
if [ "$ALL_CHECKS_PASSED" = true ]; then
    echo -e "${GREEN}All critical checks passed!${NC}"
    echo "You can proceed with building the app."
else
    echo -e "${RED}Some checks failed.${NC}"
    echo "Please resolve the issues above before building."
fi
echo "=================================="
echo ""
echo "Next steps:"
echo "  1. Ensure all checks pass"
echo "  2. Open the project in Android Studio, or"
echo "  3. Run './gradlew build' from the command line"
