plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

android {

    namespace = "com.example.news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.news"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.databinding:viewbinding:8.2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Cores
    implementation("androidx.activity:activity-ktx:1.5.0")
    implementation("androidx.fragment:fragment-ktx:1.5.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Android Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")

    // Retrofit2
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Viewmodel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")

    // Coil
    implementation ("io.coil-kt:coil:2.1.0")

    // Timber (easy logging)
    implementation ("com.jakewharton.timber:timber:5.0.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Simmer Loading Animation
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    //JSON Serializer
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    // Chucker
    implementation ("com.github.chuckerteam.chucker:library:4.0.0")

}