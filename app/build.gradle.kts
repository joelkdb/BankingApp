plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "fr.utbm.bindoomobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "fr.utbm.bindoomobile"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //implementation(libs.androidx.lifecycle.runtime.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.compose.state.events)
    implementation(libs.androidx.lifecycle.runtime.compose)
    //KeyPrefs
    implementation(libs.ksprefs)
    //Constraint layout
    implementation(libs.androidx.constraintlayout.compose)

    // Coil for compose to load images
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    //Shimmer effect loading
    implementation(libs.compose.shimmer)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    //Splashscreen
    implementation(libs.androidx.core.splashscreen)
    //Security biometric
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.biometric.ktx)
    //Paging
    implementation(libs.androidx.paging.compose)
    // QR lib
    implementation(libs.zxing.android.embedded)
    implementation(libs.core)
    //Work manager
    implementation(libs.androidx.work.runtime.ktx)
    //Koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.workmanager)
    //Google fonts
    implementation(libs.androidx.ui.text.google.fonts)
    //PremissionX
    implementation(libs.permissionx)
    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.simplexml)
    implementation (libs.logging.interceptor)
    implementation (libs.okhttp)

}