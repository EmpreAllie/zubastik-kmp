plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
}

val features = listOf(
    projects.shared.core,
    projects.shared.entity,
    projects.shared.features.base,
    projects.shared.features.ui,
    projects.shared.features.root,
    projects.shared.features.splash,
    projects.shared.features.splash.ui,
)

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
            baseName = "root-ui"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.bundles.android)
            implementation(compose.preview)
            implementation(libs.activityCompose)
        }

        commonMain.dependencies {
            features.forEach {
                implementation(it)
            }
            implementation(projects.shared.core)
            implementation(projects.shared.resources)
            implementation(projects.shared.entity)
            implementation(projects.shared.features.base)
            implementation(projects.shared.features.root)
            implementation(projects.shared.features.ui)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.composeNavigation)
            implementation(libs.kotlinSerialization)
            implementation(libs.bundles.koin.compose)
            implementation(libs.bundles.coil)
            implementation(libs.bundles.ktor)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations)
        }
    }
}

android {
    namespace = "com.features.root.ui"
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

dependencies {
    debugImplementation(compose.uiTooling)
}