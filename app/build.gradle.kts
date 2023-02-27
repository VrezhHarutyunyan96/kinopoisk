import Dependencies.load

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

var currentVersionName = ""
var appPackageName = ""

android {

    buildFeatures { viewBinding = true }

    bundle {
        language { enableSplit = false }
    }

    lint {
        baseline = file("lint-baseline.xml")
    }

    incrementVersionCode()
    if (currentVersionName.isEmpty())
        currentVersionName = buildVersionName()

    setCompileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = "com.example.kinopoisk"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = readVersionCode()
        versionName = currentVersionName
        appPackageName = applicationId ?: ""
    }


    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        kotlinOptions { jvmTarget = "1.8" }

        flavorDimensions(AppConfig.dimension)
        productFlavors {
            create("dev") {
                applicationIdSuffix = ".dev"
                versionNameSuffix = "-DEV"
                dimension = AppConfig.dimension
                manifestPlaceholders["authorities"] =
                    "${appPackageName}${applicationIdSuffix}.file_provider"
            }
            create("live") {
                dimension = AppConfig.dimension
                manifestPlaceholders["authorities"] = "${appPackageName}.file_provider"
            }
        }
    }

    dependencies {
        implementation(project(":domain"))
        implementation(project(":data"))
        load(Dependencies.appLibraries())
    }
}
