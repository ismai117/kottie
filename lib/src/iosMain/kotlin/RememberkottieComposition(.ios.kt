import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import kottieComposition.kottieComposition
import lottie.lottieComposition.LottieCompositionSpec
import lottie.lottieComposition.rememberLottieComposition


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberKottieComposition(
    spec: KottieCompositionSpec
): Any? {
    return when(spec){
        is KottieCompositionSpec.File -> {
            (kottieComposition(spec = spec) as? LottieCompositionSpec)?.let {
                rememberLottieComposition(it)
            }
        }
        is KottieCompositionSpec.Url -> {
            (kottieComposition(spec = spec) as? LottieCompositionSpec)?.let {
                rememberLottieComposition(it)
            }
        }
        is KottieCompositionSpec.JsonString -> {
            (kottieComposition(spec = spec) as? LottieCompositionSpec)?.let {
                rememberLottieComposition(it)
            }
        }
    }
}