plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                api(kotlin("test-junit"))
            }
        }
    }
}