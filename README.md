<h1 align="center">Kottie</h1></br>

<p align="center">
Compose Multiplatform animation library that parses Adobe After Effects animations. Inspired by Airbnb/Lottie.
</p>
</br>

[![Latest release](https://img.shields.io/github/v/release/ismai117/kottie?color=brightgreen&label=latest%20release)](https://github.com/ismai117/kottie/releases/latest)
[![Latest build](https://img.shields.io/github/v/release/ismai117/kottie?color=orange&include_prereleases&label=latest%20build)](https://github.com/ismai117/kottie/releases)

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

### Setup


Add the dependency in your common module's commonMain sourceSet

```
implementation("io.github.ismai117:kottie:latest_version")
```


### Usage 

 
``` kotlin

    // Spec - File, Url, JsonString

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.Url("")
    )

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        speed = 1f,
        iterations = 2
    )

    KottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = modifier.fillMaxSize(),
    )

    LaunchedEffect(
        key1 = animationState.isPlaying
    ) {
        if (animationState.isPlaying) {
            println("Animation Playing")
        }
        if (animationState.isCompleted) {
            println("Animation Completed")
        }
    }


```





