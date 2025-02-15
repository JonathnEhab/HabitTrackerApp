// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {


    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri( "https://jitpack.io" )}

    }

    dependencies {
        classpath ("com.android.tools.build:gradle:7.1.3")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
        classpath ("com.neenbedankt.gradle.plugins:android-apt:1.8")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.7")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.50")


    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false


}