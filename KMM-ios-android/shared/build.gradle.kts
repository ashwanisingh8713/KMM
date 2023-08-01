plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp").version("1.9.0-1.0.11")
    id("com.rickclephas.kmp.nativecoroutines").version("1.0.0-ALPHA-13")
    kotlin("plugin.serialization")
}

kotlin {
    android()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        val ktorVersion = "2.3.2"

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.materialIconsExtended)

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")

                // Logging plugin
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                // Coroutine Core
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")

                // Ktor Engine - An engine is responsible for processing network requests.
                // This should be used when we are not using "ktor-client-okhttp" and "ktor-client-darwin"
                // https://ktor.io/docs/http-client-engines.html#configure
                // This can be useful for multiplatform projects. For example,
                // for a project targeting both Android and iOS, you can add the Android dependency
                // to the androidMain source set and the Darwin dependency to the iosMain source set.
                // The necessary dependency will be selected at compile time.
                //implementation("io.ktor:ktor-client-cio:$ktorVersion")

                // ContentNegotiation dependency
                // Serializing/deserializing the content in a specific format when sending requests and receiving responses.
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                // Json Serialisation
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

                // ViewModel - Multiplatform
                implementation("com.rickclephas.kmm:kmm-viewmodel-core:1.0.0-ALPHA-12")

                // voyager Navigation Dependency
                val voyagerVersion = "1.0.0-rc05"
                // Navigator
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                // TabNavigator
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
                // Transitions
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                // Ktor Client
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

                // Compose Preview
                implementation(compose.preview)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                // Ktor Client
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}
