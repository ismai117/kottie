import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}

val copyJsResources = tasks.create("copyJsResourcesWorkaround", Copy::class.java) {
    from(project(":sample:shared").file("src/commonMain/composeResources"))
    into("build/processedResources/js/main")
}

afterEvaluate {
    project.tasks.getByName("jsProcessResources").finalizedBy(copyJsResources)
    project.tasks.getByName("jsBrowserProductionExecutableDistributeResources").mustRunAfter(copyJsResources)
    project.tasks.getByName("jsDevelopmentExecutableCompileSync").mustRunAfter(copyJsResources)
    project.tasks.getByName("jsProductionExecutableCompileSync").mustRunAfter(copyJsResources)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":sample:shared"))
                implementation(compose.html.core)
                implementation(compose.ui)
            }
        }
    }
}

compose.experimental {
    web.application {}
}
