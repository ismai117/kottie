package kottieComposition

import androidx.compose.runtime.Composable
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
actual fun rememberKottieComposition(
    spec: KottieCompositionSpec
): Any? {
    return when (spec) {
        is KottieCompositionSpec.File -> {
            (kottieComposition(spec = spec) as? LottieCompositionSpec)?.let {
                rememberLottieComposition(it).value
            }
        }
        is KottieCompositionSpec.Url -> {
            (kottieComposition(spec = spec) as? LottieCompositionSpec)?.let {
                rememberLottieComposition(it).value
            }
        }
//        is KottieCompositionSpec.JsonString -> {
//            (kottieComposition(spec = spec) as? LottieCompositionSpec)?.let {
//                rememberLottieComposition(it).value
//            }
//        }
    }
}