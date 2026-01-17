plugins {
    id("com.android.library")
}

android {
    namespace = "brut.androlib"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(project(":brut.j.common"))
    api(project(":brut.j.util"))
    api(project(":brut.j.dir"))
    api(project(":brut.j.xml"))
    api(project(":brut.j.yaml"))

    implementation(libs.baksmali)
    implementation(libs.smali)
    implementation(libs.guava)
    implementation(libs.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.commons.text)

    testImplementation(libs.junit)
    testImplementation(libs.xmlunit)
}

tasks {
    register("processResources") {
        doLast {
            val resourcesDir = file("src/main/resources")
            val outputDir = file("build/intermediates/library_assets/debug/packageDebugAssets")
            outputDir.mkdirs()
            
            copy {
                from(resourcesDir) {
                    include("**/*.jar")
                }
                into(outputDir)
            }
        }
    }
}
