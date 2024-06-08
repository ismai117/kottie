package skiaComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.jetbrains.skia.Data
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
                val httpClient = HttpClient()
                val data = httpClient.get(spec.url)
                Animation.makeFromData(
                    Data.makeFromBytes(data.bodyAsText().encodeToByteArray())
                )
            }

            is SkiaCompositionSpec.JsonString -> {
                if (spec.jsonString.isNotBlank()) Animation.makeFromString(spec.jsonString) else return@LaunchedEffect
            }
        }
        animationState = animation
    }
    return animationState
}

