buildscript {
    repositories { mavenCentral() }

    dependencies {
        val kotlinVersion = "1.8.20"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
    id("com.google.devtools.ksp").apply(false)
    id("com.rickclephas.kmp.nativecoroutines").apply(false)
}

