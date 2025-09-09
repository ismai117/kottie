package skiaComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kottieComposition.KottieCompositionResult
import org.jetbrains.skia.skottie.Animation


@Composable
internal fun rememberSkiaComposition(
    spec: SkiaCompositionSpec
): KottieCompositionResult {
    var animationState by remember(spec) {
        mutableStateOf(KottieCompositionResult.loading())
    }
    LaunchedEffect(spec) {
        val animation = when (spec) {
            is SkiaCompositionSpec.File -> {
                if (spec.jsonString.isNotBlank()) {
                    KottieCompositionResult.success(Animation.makeFromString(spec.jsonString))
                } else KottieCompositionResult.error(Exception("empty json"))
            }

            is SkiaCompositionSpec.Url -> {
                try {
                    val animation = Animation.makeFromString(getAnimation(url = spec.url))
                     KottieCompositionResult.success(animation)
                } catch (e: Exception) {
                    KottieCompositionResult.success(e)
                }
            }

            is SkiaCompositionSpec.JsonString -> {
                if (spec.jsonString.isNotBlank()) {
                    KottieCompositionResult.success(Animation.makeFromString(spec.jsonString))
                } else KottieCompositionResult.error(Exception("empty json"))
            }
        }
        animationState = animation
    }
    return animationState
}

expect suspend fun getAnimation(url: String): String
