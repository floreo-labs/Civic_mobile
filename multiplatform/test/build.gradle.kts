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
                implementation(project(":multiplatform:preferences"))
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))
                api("app.cash.turbine:turbine:0.2.1")
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