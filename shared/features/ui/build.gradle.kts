plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
}

android {
    namespace = "com.features.ui"
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

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


}

kotlin {
    androidTarget()
    kotlin {
        jvmToolchain(21)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ui"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.coil.gif)
            implementation(libs.activityCompose)
            implementation(libs.bundles.android)
        }
        commonMain.dependencies {
            implementation(projects.shared.core)
            implementation(projects.shared.resources)
            implementation(projects.shared.entity)
            implementation(projects.shared.features.base)

            implementation(libs.bundles.coil)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}