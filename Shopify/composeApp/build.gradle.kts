import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.application)
//    alias(libs.plugins.libres)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.apollo3)

}


@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    // Android App
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    // https://gist.github.com/karlrwjohnson/a631cbda6b2fb316bc0a9f37156eae5d
    // https://github.com/JetBrains/kotlin/blob/3e251668324f3ac2f0819ca6bef0ce97ca26f39b/libraries/tools/kotlin-gradle-plugin/src/main/kotlin/org/jetbrains/kotlin/gradle/targets/js/webpack/KotlinWebpackConfig.kt
    // Web App
    js(IR) {
        moduleName = "ShopifyPOC"
        browser {
            commonWebpackConfig {
                outputFileName = "shopifyPOC.js"
                devServer?.`open` = true
            }

            distribution {
                outputDirectory.set(projectDir.resolve("output"))
            }
        }
        binaries.executable()
    }


    /*iosX64()
    iosArm64()
    iosSimulatorArm64()*/

    // iOS App
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "composeApp"
            isStatic = true
        }
        iosTarget.setUpiOSObserver()
    }

    cocoapods {
        version = "1.0.0"
        summary = "Compose application framework"
        homepage = "empty"
        ios.deploymentTarget = "12.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
//                implementation(compose.ui)
                implementation(compose.animation)
//                implementation(libs.libres)
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.koin)
                implementation(libs.composeImageLoader)
                implementation(libs.napier)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.insetsx)
                implementation(libs.ktor.core)
                implementation(libs.ktor.json)
                implementation(libs.ktor.logging)
                implementation(libs.ktor.negotiation)

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.sqlDelight.extensions)

                // Image Caching
                implementation(libs.kamel.image)

                implementation(libs.apollo3.runtime)
                // implementation(libs.apollo3.api)
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
                // implementation("co.touchlab:kermit:2.0.0-RC5")

                // Required by Koin 3.5.3, because this version of Koin uses Stately
                implementation("co.touchlab:stately-common:2.0.5")


            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.sqlDelight.driver.android)
                implementation(libs.koin.android)
                implementation(libs.datastore.preferences)
                api("androidx.webkit:webkit:1.8.0")
                //implementation ("com.shopify.mobilebuysdk:buy3:3.2.3")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
//                implementation(libs.ktor.client.js)
                implementation(libs.sqlDelight.driver.js)
                implementation(compose.runtime)
                implementation(libs.ktor.client.js)
            }
        }


        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqlDelight.driver.native)
            }
        }
    }
}

android {
    namespace = "com.ns"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34

        applicationId = "com.ns.shopify.kmm.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packaging {
        resources.excludes.add("META-INF/**")
    }
}


compose.experimental {
    web.application {}
}


// Required By WebView
fun org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.setUpiOSObserver() {
    val path = projectDir.resolve("src/nativeInterop/cinterop/observer")

    binaries.all {
        linkerOpts("-F $path")
        linkerOpts("-ObjC")
    }

    compilations.getByName("main") {
        cinterops.create("observer") {
            compilerOpts("-F $path")
        }
    }
}


sqldelight {
    databases {
        create("AppDatabase") {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("com.ns.data_cache.sqldelight")
        }
    }
}

apollo {
    service("service") {
        packageName.set("com.ns.shopify")
        // Enable generation of metadata for use by downstream modules
        generateApolloMetadata.set(true)

        // define options that must be shared between all modules using this schema
        generateDataBuilders.set(true)
    }
}

/*libres {
    generatedClassName = "MainRes" // "Res" by default
    generateNamedArguments = true // false by default
    baseLocaleLanguageCode = "en" // "en" by default
    camelCaseNamesForAppleFramework = true // false by default
}*/

