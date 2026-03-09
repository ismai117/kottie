package kottie.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kottie.KottieComposition
import kottie.KottieCompositionSpec
import org.jetbrains.skia.skottie.Animation

@Composable
internal fun rememberSkiaComposition(spec: KottieCompositionSpec): KottieComposition {
    var state by remember(spec) { mutableStateOf<KottieComposition>(KottieComposition.Loading) }

    LaunchedEffect(spec) {
        state = when (spec) {
            is KottieCompositionSpec.File -> {
                if (spec.jsonString.isBlank()) {
                    KottieComposition.Failure(IllegalArgumentException("JSON string is empty"))
                } else {
                    try {
                        val animation = Animation.makeFromString(spec.jsonString)
                        KottieComposition.Success(animation)
                    } catch (e: Exception) {
                        KottieComposition.Failure(e)
                    }
                }
            }

            is KottieCompositionSpec.Url -> {
                try {
                    val json = fetchAnimationJson(spec.url)
                    val animation = Animation.makeFromString(json)
                    KottieComposition.Success(animation)
                } catch (e: Exception) {
                    KottieComposition.Failure(e)
                }
            }

            is KottieCompositionSpec.JsonString -> {
                if (spec.jsonString.isBlank()) {
                    KottieComposition.Failure(IllegalArgumentException("JSON string is empty"))
                } else {
                    try {
                        val animation = Animation.makeFromString(spec.jsonString)
                        KottieComposition.Success(animation)
                    } catch (e: Exception) {
                        KottieComposition.Failure(e)
                    }
                }
            }
        }
    }

    return state
}

internal expect suspend fun fetchAnimationJson(url: String): String
