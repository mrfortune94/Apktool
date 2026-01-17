plugins {
    id("com.android.library")
}

android {
    namespace = "brut.j.dir"
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
    implementation(project(":brut.j.util"))
}
