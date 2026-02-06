import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.openapi.generator)
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
            baseName = "network"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koinCore)
            implementation(libs.koinAndroid)
            implementation(libs.ktorClientOkHttp)
        }

        commonMain {
            dependencies {
                implementation(projects.shared.core)
                implementation(projects.shared.entity)
                implementation(projects.shared.features.base)
                implementation(libs.kotlinSerialization)
                implementation(libs.kotlinxDateTime)
                implementation(libs.koinCore)
                implementation(libs.multiplatformSettings)

                implementation(libs.bundles.ktor)
            }

            kotlin.srcDirs(
                "${layout.buildDirectory.get()}/generated/api/api/src/main/kotlin",
            )
        }

        iosMain.dependencies {
            implementation(libs.ktorClientDarwin)
            implementation(libs.koinCore)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    compilerOptions.optIn.add("kotlin.time.ExperimentalTime")
}

android {
    namespace = "com.network"
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
        buildConfig = true
    }

    defaultConfig {
        minSdk = 26
    }
}

val apiSpecs = listOf(
    "api" to "openapi"
)

apiSpecs.forEach { (name, specFile) ->
    tasks.register<GenerateTask>("openApiGenerate_$name") {
        generatorName.set("kotlin")
        packageName.set("com.network.$name")
        inputSpec.set("${projectDir.path}/src/commonMain/kotlin/com/network/api/$specFile.yaml")
        outputDir.set("${projectDir.path}/build/generated/api/$name")
        typeMappings.putAll(
            mapOf(
                "string+date-time" to "Instant",
                "number+int64" to "Long",
                "string+byte" to "String"
            )
        )
        templateDir = "${projectDir.path}/src/commonMain/kotlin/com/network/api/template"
        importMappings.putAll(
            mapOf(
                "Instant" to "kotlin.time.Instant",
                "Long" to "kotlin.Long",
                "String" to "kotlin.String"
            )
        )
        skipValidateSpec.set(true)
        generateApiTests.set(false)
        generateModelTests.set(false)
        configOptions.set(
            mapOf(
                "library" to "jvm-ktor",
                "dateLibrary" to "kotlinx-datetime",
                "enumPropertyNaming" to "UPPERCASE",
                "serializationLibrary" to "kotlinx_serialization",
                "useCoroutines" to "true",
                "sealedClasses" to "true",
            )
        )
    }
}

tasks.named("preBuild") {
    apiSpecs.forEach { (name, _) ->
        dependsOn("openApiGenerate_$name")
    }
}