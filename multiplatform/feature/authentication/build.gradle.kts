plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

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
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":multiplatform:test"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
    }
}

configurations.create("compileClasspath")