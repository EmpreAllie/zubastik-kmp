plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinSerialization)
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
    libs.bundles.ktor,
    libs.bundles.viewmodel,
    libs.bundles.coil,
    libs.threetenabp,
    libs.koinCore,
    libs.compose.resource,
    libs.multiplatformSettings,
)

kotlin {
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

        targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
            framework {
                baseName = "iosExport"
                isStatic = true

                freeCompilerArgs += listOf("-linker-option", "-lsqlite3")
            }
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
