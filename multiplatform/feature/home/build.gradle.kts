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
            languageSettings.useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
            languageSettings.useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":multiplatform:arch"))
                implementation(project(":multiplatform:common"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
                implementation(kotlin("stdlib"))
                implementation("androidx.appcompat:appcompat:1.2.0")
                implementation("com.google.android.gms:play-services-location:17.1.0")
            }
        }
    }
}

configurations.create("compileClasspath")