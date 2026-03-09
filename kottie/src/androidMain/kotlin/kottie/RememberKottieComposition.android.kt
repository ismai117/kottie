package kottie

import androidx.compose.runtime.Composable
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
actual fun rememberKottieComposition(spec: KottieCompositionSpec): KottieComposition {
    val lottieSpec = when (spec) {
        is KottieCompositionSpec.Url -> LottieCompositionSpec.Url(spec.url)
        is KottieCompositionSpec.File -> LottieCompositionSpec.JsonString(spec.jsonString)
        is KottieCompositionSpec.JsonString -> LottieCompositionSpec.JsonString(spec.jsonString)
    }

    val result = rememberLottieComposition(lottieSpec)

    return when {
        result.isLoading -> KottieComposition.Loading
        result.error != null -> KottieComposition.Failure(result.error!!)
        result.value != null -> KottieComposition.Success(result.value!!)
        else -> KottieComposition.Loading
    }
}
