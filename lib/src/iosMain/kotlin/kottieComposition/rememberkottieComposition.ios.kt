package kottieComposition

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import lottie.lottieComposition.LottieCompositionSpec
import lottie.lottieComposition.rememberLottieComposition

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberKottieComposition(
    spec: KottieCompositionSpec
): KottieCompositionResult {

    return (kottieComposition(spec = spec) as? LottieCompositionSpec)?.let {
        rememberLottieComposition(it)
    } ?: KottieCompositionResult.error(IllegalStateException("Failed to create LottieCompositionSpec"))
}