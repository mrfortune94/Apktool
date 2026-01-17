# Building Apktool as an Android APK

This fork of Apktool has been modified to build as an Android application APK instead of a JAR file.

## Changes Made

### Build System
- Converted all modules from Java library to Android library/application
- Added Android Gradle Plugin (AGP) 8.2.0
- Updated Gradle build files for all modules

### Android Components
- **AndroidManifest.xml**: Added for the main application and all library modules
- **MainActivity**: Created a simple Android UI to execute Apktool commands
  - Text input for commands
  - Execute button to run commands
  - Output display area showing results
  - Required permissions for file access

### Code Modifications
- Removed AWT `KeyEvent` dependency in `ResXmlEncoders.java` - replaced with constant value
- Added Android dependencies (AppCompat, Core, MultiDex)
- Updated ProGuard rules for Android compatibility

## Building the APK

### Prerequisites
- Android SDK (API 34 recommended)
- Java 8 or higher
- Gradle 8.7 or higher

### Build Steps

1. Set Android SDK location in `local.properties`:
   ```
   sdk.dir=/path/to/android-sdk
   ```

2. Build the APK:
   ```bash
   ./gradlew assembleDebug
   ```

3. The APK will be generated at:
   ```
   brut.apktool/apktool-cli/build/outputs/apk/debug/apktool-cli-debug.apk
   ```

## Known Limitations

### 9-Patch Image Processing
The `ResNinePatchStreamDecoder` class uses Java AWT (`BufferedImage`, `Raster`, etc.) for image processing, which is not available on Android. This functionality will need to be reimplemented using Android's `Bitmap` class or disabled for the Android version.

### File System Access
- Requires Android storage permissions
- File paths must be adapted for Android's file system structure
- External storage access may require scoped storage handling on Android 10+

### Command Execution
The current implementation runs Apktool commands in the UI thread. For production use, this should be moved to a background thread to prevent ANR (Application Not Responding) errors.

## Usage

1. Install the APK on an Android device
2. Grant storage permissions when prompted
3. Enter an Apktool command (e.g., "d /sdcard/app.apk")
4. Tap Execute
5. View the output in the text area below

## Future Improvements

- [ ] Move command execution to background thread
- [ ] Implement proper Android file picker for input files
- [ ] Add progress indicators for long-running operations
- [ ] Reimplement 9-patch processing using Android Bitmap API
- [ ] Add file browser UI for easier file selection
- [ ] Implement proper error handling and user feedback
- [ ] Add unit tests for Android-specific code
