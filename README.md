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

Add the dependency in your common module's commonMain sourceSet

```
implementation("io.github.ismai117:kottie:latest_version")
```
## Load Animation Composition
Load the animation composition using rememberKottieComposition function. Choose the appropriate specification for loading the composition (File, Url, or JsonString):

```
val composition = rememberKottieComposition(
    spec = KottieCompositionSpec.File("files/Animation.json") // Or use KottieCompositionSpec.Url or KottieCompositionSpec.JsonString
)
```

## Animate the Composition
Animate the loaded composition using animateKottieCompositionAsState function. Optionally, you can control the animation playback and iterations:

```
var playing by remember { mutableStateOf(false) }

val animationState by animateKottieCompositionAsState(
    composition = composition,
    speed = 1f,
    iterations = KottieConstants.IterateForever, // Or specify a number of iterations
    isPlaying = playing
)
```

## Display the Animation
Display the animation using KottieAnimation composable:

```
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

## Control Animation Playback
Control animation playback according to your app's logic. You can use LaunchedEffect to observe animation state changes:

```
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





