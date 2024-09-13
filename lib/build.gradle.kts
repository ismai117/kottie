import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    iosArm64 {
        compilations.getByName("main") {
            val Lottie by cinterops.creating {
                defFile("src/nativeInterop/cinterop/Lottie.def")
                val path = "$rootDir/vendor/Lottie.xcframework/ios-arm64"
                compilerOpts("-F$path", "-framework", "Lottie", "-rpath", path)
                extraOpts += listOf("-compiler-option", "-fmodules")
            }
        }
    }

    listOf(
        iosX64(),
        iosSimulatorArm64()
    ).forEach {
        it.compilations.getByName("main") {
            val Lottie by cinterops.creating {
                defFile("src/nativeInterop/cinterop/Lottie.def")
                val path = "$rootDir/vendor/Lottie.xcframework/ios-arm64_x86_64-simulator"
                compilerOpts("-F$path", "-framework", "Lottie", "-rpath", path)
                extraOpts += listOf("-compiler-option", "-fmodules")
            }
        }
    }


    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)
            }
        }

        val skiaMain = create("skiaMain") {
            dependsOn(commonMain)
        }

        val androidMain by getting {
            dependencies {
                api(libs.androidx.activityCompose)
                implementation(libs.androidLottie)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependsOn(commonMain)
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.darwin)
            }
        }

        val jvmMain by getting {
            dependsOn(skiaMain)
        }

        val jsMain by getting {
            dependsOn(skiaMain)
        }

        val wasmJsMain by getting {
            dependsOn(skiaMain)
        }

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
}

mavenPublishing {
//    publishToMavenCentral(SonatypeHost.DEFAULT)
    // or when publishing to https://s01.oss.sonatype.org
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()
    coordinates("io.github.ismai117", "kottie", "2.0.0")

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

