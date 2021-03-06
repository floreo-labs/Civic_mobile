plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
    }
}

configurations.create("compileClasspath")