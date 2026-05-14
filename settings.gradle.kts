enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
            }
        }
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Zubastik"
include(":androidApp")
include(":androidApp:features")
include(":androidApp:features:root")
include(":shared")
include(":shared:entity")
include(":shared:database")
include(":shared:resources")
include(":shared:core")
include(":shared:network")
include(":shared:features:base")
include(":shared:features:ui")
include(":shared:features:root")
include(":shared:features:root:ui")
include(":shared:features:splash")
include(":shared:features:splash:ui")
include(":shared:features:auth")
include(":shared:features:auth:ui")
include(":shared:features:onboard")
include(":shared:features:onboard:ui")
include(":shared:features:teeth")
include(":shared:features:teeth:ui")
include(":shared:features:main")
include(":shared:features:main:ui")
include(":shared:features:calendar")
include(":shared:features:calendar:ui")
include(":shared:features:ai")
include(":shared:features:ai:ui")
include(":iosExport")
