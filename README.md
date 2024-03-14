[![Latest release](https://img.shields.io/github/v/release/ismai117/kottie?color=brightgreen&label=latest%20release)](https://github.com/ismai117/kottie/releases/latest)
[![Latest build](https://img.shields.io/github/v/release/ismai117/kottie?color=orange&include_prereleases&label=latest%20build)](https://github.com/ismai117/kottie/releases)
<br>
 
<h1 align="center">Kottie</h1></br>

<p align="center">
Compose Multiplatform animation library that parses Adobe After Effects animations. Inspired by Airbnb/Lottie.
</p>
</br>

<p align="center">
  <img alt="Platform Android" src="https://img.shields.io/badge/Platform-Android-brightgreen"/>
  <img alt="Platform iOS" src="https://img.shields.io/badge/Platform-iOS-lightgray"/>
  <img alt="Platform JVM" src="https://img.shields.io/badge/Platform-JVM-orange"/>
  <img alt="Platform Js" src="https://img.shields.io/badge/Platform-Js-yellow"/>

<p align="center">
  <img align="center" src="https://github.com/ismai117/kottie/assets/88812838/1f46e16b-2fff-4fff-8a33-5d954b9e0c03" alt="Kottie" width="400"/>
</p> </br>

## Getting Started

To integrate Kottie into your Kotlin Multiplatform project

Add the dependency in your common module's commonMain source set

```
implementation("io.github.ismai117:kottie:latest_version")
```

Add the lottie-ios pod inside the cocoapods block

```
pod("lottie-ios") {
    version = "4.4.0"
    linkOnly = true
}
```

Note: If you don't have cocoapods configured inside your project, then do the following steps:

- In build.gradle (.kts) of your project, apply the CocoaPods plugin
  ```
  plugins {
    kotlin("multiplatform") version "1.9.23"
    kotlin("native.cocoapods") version "1.9.23"
  }
  ```
- Configure the version, summary, homepage, deployment target, podfile, which you will add in the next step, and lastly, the lottie-ios pod in the CocoaPods block:
  ```
  iosX64()
  iosArm64()
  iosSimulatorArm64()

  cocoapods {
    summary = "Some description for the Shared Module"
    homepage = "Link to the Shared Module homepage"
    version = "1.0"
    ios.deploymentTarget = "17.2"
    podfile = project.file("../iosApp/Podfile")
    pod("lottie-ios") {
       version = "4.4.0"
       linkOnly = true
    }
  }
  
  ```
- Create the podfile for your iOS app with the following commands:
  - pod init
  - pod install

- Add the following lines inside the created podfile.

  ```
  target 'iosApp' do
    use_frameworks!
    platform :ios, '17.2'
  pod 'composeApp', :path => '../composeApp'
  end
  ```

- Change the Xcode project file path from iosApp.xcodeproj to iosApp.xcworkspace.
 <img src="https://github.com/ismai117/kottie/assets/88812838/7a23884c-0be3-4a63-b2b5-67b9e3f0cae8" height=400>


## Load Animation Composition

Load the animation composition using rememberKottieComposition function. Choose the appropriate specification for loading the composition (File, Url, or JsonString).

```Kotlin
val composition = rememberKottieComposition(
    spec = KottieCompositionSpec.File("files/Animation.json") // Or KottieCompositionSpec.Url || KottieCompositionSpec.JsonString
)
```

## Display the Animation

Display the animation using KottieAnimation composable

```Kotlin
MaterialTheme {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        KottieAnimation(
            composition = composition,
            progress = { animationState.progress },
            modifier = modifier.size(300.dp)
        )

    }
}
```

## Control Animation Playback

You can control animation playback by using a mutableStateOf variable to toggle the animation on and off.

```Kotlin
var playing by remember { mutableStateOf(false) }

val animationState by animateKottieCompositionAsState(
    composition = composition,
    isPlaying = playing
)

MaterialTheme {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        KottieAnimation(
            composition = composition,
            progress = { animationState.progress },
            modifier = modifier
                .size(300.dp)
        )

        Button(
           onClick = {
              playing = true
           }
        ){
           Text("Play")
        }

    }
}

```

## Adjusting Speed

To change the playback speed of the animation, modify the speed parameter in the animateKottieCompositionAsState function. By default, the speed is set to 1f, indicating normal speed playback. You can increase the speed for faster playback or decrease it for slower playback.

```Kotlin
val animationState by animateKottieCompositionAsState(
    composition = composition,
    speed = 1.5f // Adjust the speed as needed
)
```

## Set Iterations

By default, the animation plays once and stops (iterations = 1). You can specify the number of times the animation should repeat using the iterations parameter. Alternatively, you can set it to KottieConstants.IterateForever for the animation to loop indefinitely.

```Kotlin
val animationState by animateKottieCompositionAsState(
    composition = composition,
    iterations = 3 // Play the animation 3 times
)
```

## Observing Animation State

You can observe animation state changes:


```Kotlin
LaunchedEffect(
    key1 = animationState.isPlaying
) {
    if (animationState.isPlaying) {
        println("Animation Playing")
    }
    if (animationState.isCompleted) {
        println("Animation Completed")
        playing = false
    }
}
```


