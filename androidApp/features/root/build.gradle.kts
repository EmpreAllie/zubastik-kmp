import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.zubastik.features.root"
    compileSdk = 36

    flavorDimensions.add(0, "jni")

    productFlavors {
        create("dev") {
            dimension = "jni"
            matchingFallbacks.add("dev")
        }

        create("prod") {
            dimension = "jni"
            matchingFallbacks.add("prod")
        }
    }

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = 26
    }

    kotlin {
        jvmToolchain(21)
    }
}

dependencies {
    implementation(projects.shared.core)
    implementation(projects.shared.features.base)
    implementation(projects.shared.features.root)
    implementation(projects.shared.features.root.ui)

    implementation(libs.bundles.android)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin.android)
    implementation(libs.bundles.coil)
    implementation(libs.kotlinSerialization)
    implementation(libs.composeNavigation)

    debugImplementation(libs.compose.ui.tooling.preview)
}