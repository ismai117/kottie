package kottieComposition

import KottieCompositionSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.airbnb.lottie.compose.LottieCompositionSpec
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource


@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun kottieComposition(
    spec: KottieCompositionSpec
): Any? {
    var lottieSpec by remember { mutableStateOf<LottieCompositionSpec?>(null) }
    LaunchedEffect(spec) {
        lottieSpec = when (spec) {
            is KottieCompositionSpec.File -> LottieCompositionSpec.JsonString(
                resource(spec.path).readBytes().decodeToString()
            )
            is KottieCompositionSpec.Url -> LottieCompositionSpec.Url(spec.url)
            is KottieCompositionSpec.JsonString -> LottieCompositionSpec.JsonString(spec.jsonString)
        }
    }
    return lottieSpec
}