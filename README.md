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
  <img alt="Platform Js" src="https://img.shields.io/badge/Platform-Wasm-red"/>
</p> <br>

<p align="center">
  <img align="center" src="https://github.com/ismai117/kottie/assets/88812838/1f46e16b-2fff-4fff-8a33-5d954b9e0c03" alt="Kottie" width="400"/>
</p> </br>

## Getting Started

To integrate Kottie into your Kotlin Multiplatform project

Add the dependency in your common module's commonMain source set

```
implementation("io.github.ismai117:kottie:latest_version")
```

Load Animation Composition

Load the animation composition using rememberKottieComposition function. Choose the appropriate specification for loading the composition (File, Url, or JsonString):

```kotlin
val composition = rememberKottieComposition(
    spec = KottieCompositionSpec.File("files/Animation.json") // Or KottieCompositionSpec.Url || KottieCompositionSpec.JsonString
)
```

Display the Animation

Display the animation using KottieAnimation composable

```kotlin
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

Control Animation Playback

Now, let's explore how to control animation playback and adjust parameters.

Controlling Playback
You can control animation playback by using a mutableStateOf variable to toggle the animation on and off:

```kotlin
var playing by remember { mutableStateOf(false) }

val animationState by animateKottieCompositionAsState(
    composition = composition,
    isPlaying = playing
)
```

Adjusting Speed

To change the playback speed of the animation, modify the speed parameter in the animateKottieCompositionAsState function. By default, the speed is set to 1f, indicating normal speed playback. You can increase the speed for faster playback or decrease it for slower playback.

```kotlin
val animationState by animateKottieCompositionAsState(
    composition = composition,
    speed = 1.5f // Adjust the speed as needed
)
```

Setting Iterations

By default, the animation plays once and stops (iterations = 1). You can specify the number of times the animation should repeat using the iterations parameter. Alternatively, you can set it to KottieConstants.IterateForever for the animation to loop indefinitely.

```kotlin
val animationState by animateKottieCompositionAsState(
    composition = composition,
    iterations = 3 // Play the animation 3 times
)
```

Observing Animation State

You can observe animation state changes using LaunchedEffect. For example, to print messages when the animation starts playing and when it completes:

```kotlin
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





