# Kottie
[![Maven metadata URL](https://img.shields.io/maven-metadata/v?color=blue&metadataUrl=https://s01.oss.sonatype.org/service/local/repo_groups/public/content/io/github/ismai117/kottie/maven-metadata.xml&style=for-the-badge)](https://repo.maven.apache.org/maven2/io/github/ismai117/kottie/)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.21-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![compose-jb-version](https://img.shields.io/badge/compose--jb-1.5.11-red)](https://github.com/JetBrains/compose-jb)

![badge-Android](https://img.shields.io/badge/Platform-Android-brightgreen)
![badge-iOS](https://img.shields.io/badge/Platform-iOS-lightgray)
![badge-JVM](https://img.shields.io/badge/Platform-JVM-orange)
![badge-web](https://img.shields.io/badge/Platform-Js-yellow)

## Compose Multiplatform animation library that parses Adobe After Effects animations. Inspired by Airbnb/Lottie

https://github.com/ismai117/kottie/assets/88812838/0fd00aaa-f4a1-4b7a-afd9-cd4f1a680669


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





