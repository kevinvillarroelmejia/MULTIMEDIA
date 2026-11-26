plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.roomwithaview"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.roomwithaview"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.room3.common)
    implementation(libs.androidx.room3.runtime)
    implementation(libs.material)
    // Room
    val room_version = "3.0.3"
    implementation("andoidx.room3:room3-runtime:$room_version")
    ksp("androidx.room3:room3-compiler:$room_version")


    // --- Componentes de Arquitectura (Lifecycle & LiveData) ---
    implementation(libs.androidx.lifecycle.livedata.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}