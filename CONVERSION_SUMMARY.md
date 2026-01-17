# Apktool Android APK Conversion Summary

## Overview
This document summarizes the conversion of the Apktool project from building a JAR file to building an Android APK.

## Modified Files

### Root Level
- **build.gradle.kts**: 
  - Added Android Gradle Plugin (AGP) 8.2.0
  - Removed Java library and Maven publish plugins
  - Disabled Maven publishing configuration
  - Changed default task to `assembleDebug`

- **.gitignore**: Added `local.properties`

- **local.properties**: Created for Android SDK path configuration

### Module: brut.apktool/apktool-cli
- **build.gradle.kts**: 
  - Changed from `application` plugin to `com.android.application`
  - Added Android configuration (compileSdk, minSdk, targetSdk, etc.)
  - Added Android dependencies (AppCompat, Core, MultiDex)
  - Removed shadowJar and proguard tasks (replaced with Android build process)
  - Added resource processing configuration

- **proguard-rules.pro**: 
  - Added MainActivity keep rule
  - Added Apktool class keep rules
  - Added Android-specific dontwarn rules

- **src/main/AndroidManifest.xml**: Created with:
  - Storage permissions
  - MainActivity declaration
  - Application configuration

- **src/main/java/brut/apktool/MainActivity.java**: Created Android Activity with:
  - Command input field
  - Execute button
  - Output display
  - Permission handling

- **src/main/res/layout/activity_main.xml**: Created UI layout

- **src/main/res/values/strings.xml**: Created string resources

- **src/main/res/mipmap-mdpi/ic_launcher.xml**: Created launcher icon

### Module: brut.apktool/apktool-lib
- **build.gradle.kts**: 
  - Changed to `com.android.library` plugin
  - Added Android configuration
  - Modified resource processing for Android

- **src/main/AndroidManifest.xml**: Created empty manifest

- **src/main/java/brut/androlib/res/xml/ResXmlEncoders.java**:
  - Removed `import java.awt.event.KeyEvent`
  - Added `CHAR_UNDEFINED` constant
  - Replaced `KeyEvent.CHAR_UNDEFINED` with local constant

### Modules: brut.j.common, brut.j.util, brut.j.dir, brut.j.xml, brut.j.yaml
For each module:
- **build.gradle.kts**: 
  - Changed to `com.android.library` plugin
  - Added Android configuration with namespace, compileSdk, minSdk
  - Added Java 8 compatibility settings

- **src/main/AndroidManifest.xml**: Created empty manifest for each

## New Files
- **ANDROID_BUILD.md**: Comprehensive build documentation
- **CONVERSION_SUMMARY.md**: This file

## Technical Details

### Android Configuration
- **compileSdk**: 34
- **minSdk**: 21 (Android 5.0 Lollipop)
- **targetSdk**: 34
- **Java Version**: 8

### Dependencies Added
- androidx.appcompat:appcompat:1.6.1
- androidx.core:core:1.12.0
- androidx.multidex:multidex:2.0.1

### Known Issues
1. **9-Patch Image Processing**: The `ResNinePatchStreamDecoder` class uses Java AWT which is not available on Android. This will require reimplementation using Android's Bitmap API.

2. **File System Access**: The application requires proper Android storage permissions and scoped storage handling for Android 10+.

3. **Background Processing**: Command execution currently runs on the UI thread. Should be moved to background thread for production use.

4. **Network Dependencies**: Build may fail in sandboxed environments without internet access to download dependencies.

## Build Instructions

1. Set Android SDK path in `local.properties`:
   ```
   sdk.dir=/path/to/android-sdk
   ```

2. Build the APK:
   ```bash
   ./gradlew assembleDebug
   ```

3. Output location:
   ```
   brut.apktool/apktool-cli/build/outputs/apk/debug/apktool-cli-debug.apk
   ```

## Testing
The conversion has been completed with minimal changes to the codebase. The following areas require testing:
- APK installation on Android device
- MainActivity UI functionality
- Command execution
- File I/O operations
- Permission handling

## Future Work
- Reimplement 9-patch processing for Android
- Add background task handling with WorkManager or AsyncTask
- Implement file picker UI
- Add progress indicators
- Comprehensive error handling
- Unit tests for Android-specific code
