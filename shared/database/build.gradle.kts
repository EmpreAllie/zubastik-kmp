plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sql.delight)
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
            baseName = "database"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sql.delight.driver)
        }
        commonMain.dependencies {
            implementation(projects.shared.core)
            implementation(projects.shared.resources)
            implementation(projects.shared.entity)
            implementation(libs.kotlinSerialization)
            implementation(libs.kotlinxDateTime)
            implementation(libs.koinCore)
            implementation(libs.sql.delight.runtime)
            implementation(libs.sql.delight.coroutines)
        }
        iosMain.dependencies {
            implementation(libs.sql.delight.native)
        }
    }
}


android {
    namespace = "com.database"
    compileSdk = 36
    defaultConfig {
        minSdk = 26
    }

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

}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName = "com.database"
        }
    }
}
