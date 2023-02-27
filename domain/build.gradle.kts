import Dependencies.load

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    buildFeatures { viewBinding = true }

    setCompileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion = AppConfig.buildToolsVersion
    defaultConfig {
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures { viewBinding = true }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        kotlinOptions { jvmTarget = "1.8" }
    }

    dependencies {
        implementation(project(":data"))
        load(Dependencies.domainLibraries())
    }
}