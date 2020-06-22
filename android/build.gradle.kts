plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("koin")
}

apply("../keys.properties")

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.civic"
        minSdkVersion(22)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val key = project.property("OPEN_STATES") as String
        buildConfigField("String", "OPEN_STATES", "\"${key}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":shared"))

    // KOTLIN
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
    implementation("org.koin:koin-core:2.1.6")
    implementation("org.koin:koin-androidx-scope:2.1.6")
    implementation("org.koin:koin-androidx-fragment:2.1.6")

    // ANDROID
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta6")
    implementation("com.google.android.material:material:1.1.0")
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation("com.airbnb.android:epoxy:4.0.0-beta4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-alpha03")
    kapt("com.airbnb.android:epoxy-processor:4.0.0-beta4")

    implementation("org.reduxkotlin:redux-kotlin-jvm:0.4.0")

    // LOCATION
    implementation("com.google.android.gms:play-services-location:17.0.0")

    // DEBUG
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.3")
}

configurations.create("compileClasspath")
