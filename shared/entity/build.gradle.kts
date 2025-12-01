plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget()
    kotlin {
        jvmToolchain(21)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "entity"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.resources)
            implementation(libs.kotlinSerialization)
            implementation(libs.kotlinxDateTime)
        }
    }
}

android {
    namespace = "com.entity"
    compileSdk = 36
    defaultConfig {
        minSdk = 26
    }
}
