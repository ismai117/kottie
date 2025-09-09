rootProject.name = "Kottie"

include(":sample:androidApp")
include(":kottie")
include(":sample:desktopApp")
include(":sample:shared")
include(":sample:webApp")
include(":sample:webApp-wasm")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.4.0")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
