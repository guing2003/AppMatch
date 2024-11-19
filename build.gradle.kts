// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) version "8.6.0" apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) version "1.9.10" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}