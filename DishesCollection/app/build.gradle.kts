plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.dishescollection"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dishescollection"
        minSdk = 27
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
}

dependencies {

    // images dependency
    implementation(libs.picasso)

    // retrofit and json dependency
    implementation(libs.retrofit.v230)
    implementation(libs.converter.gson.v230)

    // rxjava dependency
    implementation(libs.rxjava2.rxjava)
    implementation(libs.rxjava2.rxandroid)
    implementation(libs.adapter.rxjava2)

    // to get text
    implementation(libs.converter.scalars)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}