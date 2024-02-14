<h1 align="center">Kottie</h1></br>

<p align="center">
Compose Multiplatform animation library that parses Adobe After Effects animations. Inspired by Airbnb/Lottie.
</p>
</br>

<p align="center">
  <a href="https://maven-badges.herokuapp.com/maven-central/io.github.ismai117/kottie"><img alt="Maven Central" src="https://maven-badges.herokuapp.com/maven-central/io.github.ismai117/kottie/badge.svg"/></a>
  <a href="https://kotlinlang.org"><img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.9.21-blue.svg?style=flat&logo=kotlin"/></a>
  <a href="https://github.com/JetBrains/compose-jb"><img alt="compose-jb-version" src="https://img.shields.io/badge/compose--jb-1.6.0--alpha01"/></a><br>
  <img alt="Platform Android" src="https://img.shields.io/badge/Platform-Android-brightgreen"/>
  <img alt="Platform iOS" src="https://img.shields.io/badge/Platform-iOS-lightgray"/>
  <img alt="Platform JVM" src="https://img.shields.io/badge/Platform-JVM-orange"/>
  <img alt="Platform Js" src="https://img.shields.io/badge/Platform-Js-yellow"/>
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





