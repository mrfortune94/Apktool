val gitRevision: String by rootProject.extra
val apktoolVersion: String by rootProject.extra

plugins {
    id("com.android.application")
}

android {
    namespace = "brut.apktool"
    compileSdk = 34

    defaultConfig {
        applicationId = "brut.apktool"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = apktoolVersion

        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/NOTICE"
        }
    }
}

dependencies {
    implementation(project(":brut.apktool:apktool-lib"))
    implementation(libs.commons.cli)
    implementation(libs.commons.io)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core:1.12.0")
    implementation("androidx.multidex:multidex:2.0.1")
}

tasks {
    register("processResources") {
        doLast {
            val resourcesDir = file("src/main/resources")
            val outputDir = file("build/intermediates/assets/debug/mergeDebugAssets")
            outputDir.mkdirs()
            
            resourcesDir.listFiles()?.forEach { file ->
                if (file.name == "apktool.properties") {
                    val content = file.readText()
                        .replace("\${version}", apktoolVersion)
                        .replace("\${gitrev}", gitRevision)
                    File(outputDir, file.name).writeText(content)
                }
            }
        }
    }
}
