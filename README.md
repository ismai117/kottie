[![Latest release](https://img.shields.io/github/v/release/ismai117/kottie?color=brightgreen&label=latest%20release)](https://github.com/ismai117/kottie/releases/latest)
[![Latest build](https://img.shields.io/github/v/release/ismai117/kottie?color=orange&include_prereleases&label=latest%20build)](https://github.com/ismai117/kottie/releases)

<h1 align="center">Kottie</h1>

<p align="center">
Compose Multiplatform animation library that parses Adobe After Effects animations. Inspired by Airbnb/Lottie.
</p>

<p align="center">
  <img alt="Platform Android" src="https://img.shields.io/badge/Platform-Android-brightgreen"/>
  <img alt="Platform iOS" src="https://img.shields.io/badge/Platform-iOS-lightgray"/>
  <img alt="Platform JVM" src="https://img.shields.io/badge/Platform-JVM-orange"/>
  <img alt="Platform Js" src="https://img.shields.io/badge/Platform-Js-yellow"/>
  <img alt="Platform Wasm" src="https://img.shields.io/badge/Platform-Wasm-purple"/>
</p>

<p align="center">
  <img src="https://github.com/ismai117/kottie/assets/88812838/1f46e16b-2fff-4fff-8a33-5d954b9e0c03" alt="Kottie" width="400"/>
</p>

## Installation

Add the dependency in your common module's `commonMain` source set:

```kotlin
implementation("io.github.ismai117:kottie:latest_version")
```

### iOS Setup

For iOS, add the Lottie framework via Swift Package Manager:

1. In Xcode, select **File → Add Packages...**
2. Enter `https://github.com/airbnb/lottie-spm.git`
3. Go to **File → Packages → Resolve Package Versions**

## Usage

### Basic Example

```kotlin
// Load composition
val composition = rememberKottieComposition(
    KottieCompositionSpec.Url("https://example.com/animation.json")
)

// Animate
val animationState by animateKottieCompositionAsState(
    composition = composition,
    iterations = Kottie.IterateForever
)

// Display
KottieAnimation(
    composition = composition,
    progress = { animationState.progress },
    modifier = Modifier.size(300.dp)
)
```

### Loading Compositions

Load animations from different sources using `KottieCompositionSpec`:

```kotlin
// From URL
val composition = rememberKottieComposition(
    KottieCompositionSpec.Url("https://example.com/animation.json")
)

// From file (using Compose Resources)
var json by remember { mutableStateOf("") }
LaunchedEffect(Unit) {
    json = Res.readBytes("files/animation.json").decodeToString()
}
val composition = rememberKottieComposition(KottieCompositionSpec.File(json))

// From JSON string
val composition = rememberKottieComposition(
    KottieCompositionSpec.JsonString("""{"v":"5.7.4",...}""")
)
```

### Handling Loading States

`KottieComposition` is a sealed type with three states:

```kotlin
when (composition) {
    is KottieComposition.Loading -> {
        CircularProgressIndicator()
    }
    is KottieComposition.Success -> {
        KottieAnimation(
            composition = composition,
            progress = { animationState.progress }
        )
    }
    is KottieComposition.Failure -> {
        Text("Error: ${composition.error.message}")
    }
}
```

### Controlling Playback

```kotlin
var isPlaying by remember { mutableStateOf(true) }

val animationState by animateKottieCompositionAsState(
    composition = composition,
    isPlaying = isPlaying
)

Button(onClick = { isPlaying = !isPlaying }) {
    Text(if (isPlaying) "Pause" else "Play")
}
```

### Speed

```kotlin
val animationState by animateKottieCompositionAsState(
    composition = composition,
    speed = 1.5f
)
```

### Iterations

```kotlin
// Play 3 times
val animationState by animateKottieCompositionAsState(
    composition = composition,
    iterations = 3
)

// Loop forever
val animationState by animateKottieCompositionAsState(
    composition = composition,
    iterations = Kottie.IterateForever
)
```

### Observing Animation State

```kotlin
val animationState by animateKottieCompositionAsState(
    composition = composition,
    iterations = Kottie.IterateForever
)

LaunchedEffect(animationState) {
    println("Progress: ${animationState.progress}")
    println("Playing: ${animationState.isPlaying}")
    println("Completed: ${animationState.isCompleted}")
    println("Iteration: ${animationState.iteration}")
}
```

## License

```
Copyright 2024 ismai117

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
