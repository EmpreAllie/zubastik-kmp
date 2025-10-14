import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.zubastikmobile.features.root"
    compileSdk = 35

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = JvmTarget.JVM_18.target
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
    implementation(libs.bundles.moko.mvvm)
    implementation(libs.bundles.coil)
    implementation(libs.mokoNetworkErrors)
    implementation(libs.kotlinSerialization)
    implementation(libs.composeNavigation)

    debugImplementation(libs.compose.ui.tooling.preview)
}