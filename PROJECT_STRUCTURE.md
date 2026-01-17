# Apktool Android APK Project Structure

## Module Architecture

```
Apktool (root)
├── brut.apktool
│   ├── apktool-cli (Android Application) ← Main APK
│   │   ├── AndroidManifest.xml
│   │   ├── MainActivity.java
│   │   ├── Main.java (CLI entry point)
│   │   └── res/
│   │       ├── layout/activity_main.xml
│   │       ├── values/strings.xml
│   │       └── mipmap-mdpi/ic_launcher.xml
│   │
│   └── apktool-lib (Android Library)
│       ├── AndroidManifest.xml
│       └── src/main/java/brut/androlib/ (core Apktool logic)
│
├── brut.j.common (Android Library)
│   └── AndroidManifest.xml
│
├── brut.j.util (Android Library)
│   └── AndroidManifest.xml
│
├── brut.j.dir (Android Library)
│   └── AndroidManifest.xml
│
├── brut.j.xml (Android Library)
│   └── AndroidManifest.xml
│
└── brut.j.yaml (Android Library)
    └── AndroidManifest.xml
```

## Dependency Graph

```
apktool-cli (Android App)
    ↓
apktool-lib (Android Library)
    ↓
    ├── brut.j.common
    ├── brut.j.util ──→ brut.j.common
    ├── brut.j.dir ──→ brut.j.common, brut.j.util
    ├── brut.j.xml
    └── brut.j.yaml
```

## Build Outputs

### Debug Build
```
./gradlew assembleDebug
→ brut.apktool/apktool-cli/build/outputs/apk/debug/apktool-cli-debug.apk
```

### Release Build (with signing)
```
./gradlew assembleRelease
→ brut.apktool/apktool-cli/build/outputs/apk/release/apktool-cli-release.apk
```

## Android Components

### Main Application Module (apktool-cli)
- **Package**: brut.apktool
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)
- **Activities**: 
  - MainActivity (launcher activity)
- **Permissions**:
  - READ_EXTERNAL_STORAGE
  - WRITE_EXTERNAL_STORAGE
  - MANAGE_EXTERNAL_STORAGE

### Library Modules
All library modules:
- **Compile SDK**: 34
- **Min SDK**: 21
- **Java Version**: 8

## Key Files

### Configuration Files
- `build.gradle.kts` (root) - Android Gradle Plugin configuration
- `settings.gradle.kts` - Module includes
- `local.properties` - Android SDK path (gitignored)
- `gradle/libs.versions.toml` - Dependency versions

### Documentation
- `ANDROID_BUILD.md` - Build instructions
- `CONVERSION_SUMMARY.md` - Detailed conversion changes
- `PROJECT_STRUCTURE.md` - This file
- `README.md` - Original Apktool README

### Android Resources
- `brut.apktool/apktool-cli/src/main/AndroidManifest.xml` - App manifest
- `brut.apktool/apktool-cli/src/main/res/` - Android resources
- `brut.apktool/apktool-cli/proguard-rules.pro` - ProGuard configuration

## Gradle Tasks

### Common Tasks
- `./gradlew tasks` - List all available tasks
- `./gradlew assembleDebug` - Build debug APK
- `./gradlew assembleRelease` - Build release APK
- `./gradlew clean` - Clean build outputs
- `./gradlew build` - Build all variants

### Android-Specific Tasks
- `./gradlew installDebug` - Install debug APK on connected device
- `./gradlew uninstallDebug` - Uninstall debug APK
- `./gradlew bundleRelease` - Build Android App Bundle
