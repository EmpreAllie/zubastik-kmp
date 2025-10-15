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
        maven { url = uri("https://jitpack.io")}
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
include(":iosExport")

