// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.1")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}