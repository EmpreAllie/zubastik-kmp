import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    id("dev.icerock.mobile.multiplatform-network-generator")
}

val features = listOf(
    projects.shared,
    projects.shared.core,
    projects.shared.entity,
    projects.shared.network,
    projects.shared.resources,
    projects.shared.database,
    projects.shared.features.splash,
    projects.shared.features.root,
    projects.shared.features.base,
    //projects.shared.features.ui,
)

val apiLibs = listOf(
    libs.kotlinxDateTime,
    libs.kotlinSerialization,
    libs.bundles.koin.compose,
    libs.bundles.moko.mvvm,
    libs.bundles.moko.network,
    libs.bundles.ktor,
    libs.bundles.coil,
    libs.mokoGraphics,
    libs.threetenabp,
    libs.koinCore,
    libs.multiplatformSettings,
)

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JvmTarget.JVM_18.target
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "iosExport"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Multiplatform Library for iOS App"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "iosExport"

            features.forEach(::export)
            apiLibs.forEach(::export)
        }
    }

    sourceSets {
        commonMain.dependencies {
            features.forEach { api(it) }
            apiLibs.forEach { api(it) }
        }

        iosMain.dependencies {
            apiLibs.forEach(::implementation)
        }
    }
}

android {
    namespace = "com.zubastikmobile"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
        missingDimensionStrategy("jni", "dev")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    buildTypes {
        getByName("debug") {
            matchingFallbacks += "dev";
        }
        getByName("release") {
            matchingFallbacks += "prod";
        }
    }
}

