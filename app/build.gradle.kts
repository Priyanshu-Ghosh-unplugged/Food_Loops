plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.foodloops"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.foodloops"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.core)
    implementation(libs.google.material)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //UI components
    // UI Components
    implementation (libs.circleimageview)  // Circular ImageView
    implementation (libs.github.glide)  // Image loading
    annotationProcessor (libs.glide.compiler)

    // Other utilities
    implementation (libs.jakewharton.timber)  // Logging
    //implementation (libs.mpandroidchart)  // Charts

    // Testing
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit.v114)
    androidTestImplementation (libs.androidx.espresso.core.v350)
}