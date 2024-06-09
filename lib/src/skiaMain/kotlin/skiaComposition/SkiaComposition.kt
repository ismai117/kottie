package skiaComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.skia.skottie.Animation


@Composable
internal fun rememberSkiaComposition(
    spec: SkiaCompositionSpec
): Animation? {
    var animationState by remember(spec) {
        mutableStateOf<Animation?>(null)
    }
    LaunchedEffect(spec) {
        val animation = when (spec) {
            is SkiaCompositionSpec.File -> {
                if (spec.jsonString.isNotBlank()) Animation.makeFromString(spec.jsonString) else return@LaunchedEffect
            }

            is SkiaCompositionSpec.Url -> {
                Animation.makeFromString(getAnimation(url = spec.url))
            }

            is SkiaCompositionSpec.JsonString -> {
                if (spec.jsonString.isNotBlank()) Animation.makeFromString(spec.jsonString) else return@LaunchedEffect
            }
        }
        animationState = animation
    }
    return animationState
}

expect suspend fun getAnimation(url: String): String
