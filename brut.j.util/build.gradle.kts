plugins {
    id("com.android.library")
}

android {
    namespace = "brut.j.util"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":brut.j.common"))
    implementation(libs.commons.io)
    implementation(libs.guava)
}
