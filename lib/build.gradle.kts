import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }

    jvm("desktop")

//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//        binaries.executable()
//    }

    js {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "lib"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "17.0"
        pod("lottie-ios"){
            version = "4.4.0"
            moduleName = "Lottie"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.ktor.client.core)
            }
        }

        val skiaMain = create("skiaMain") {
            dependsOn(commonMain)
        }

        val androidMain by getting {
            dependencies {
                api(libs.androidx.activityCompose)
                api(libs.androidx.appcompat)
                api(libs.androidx.core.ktx)
                implementation(libs.androidLottie)
                implementation(libs.ktor.client.android)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            dependsOn(skiaMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        val desktopMain by getting {
            dependsOn(skiaMain)
            dependencies {
                implementation(libs.ktor.client.java)
            }
        }

        val jsMain by getting {
            dependsOn(skiaMain)
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }

//        val wasmJsMain by getting {
//            dependsOn(skiaMain)
//        }


    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

mavenPublishing {
//    publishToMavenCentral(SonatypeHost.DEFAULT)
    // or when publishing to https://s01.oss.sonatype.org
    publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
    signAllPublications()
    coordinates("io.github.ismai117", "kottie", "1.5.1-SNAPSHOT")

    pom {
        name.set(project.name)
        description.set("Kotlin Multiplatform Animation Library")
        inceptionYear.set("2024")
        url.set("https://github.com/ismai117/kottie/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("ismai117")
                name.set("ismai117")
                url.set("https://github.com/ismai117/")
            }
        }
        scm {
            url.set("https://github.com/ismai117/kottie/")
            connection.set("scm:git:git://github.com/ismai117/kottie.git")
            developerConnection.set("scm:git:ssh://git@github.com/ismai117/kottie.git")
        }
    }
}

