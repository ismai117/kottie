package kottieComposition

import androidx.compose.runtime.Composable
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
actual fun rememberKottieComposition(
    spec: KottieCompositionSpec
): KottieCompositionResult {
    return (kottieComposition(spec = spec) as? LottieCompositionSpec)?.let { lottieSpec ->
        val lottieResult = rememberLottieComposition(lottieSpec)
        KottieCompositionResult(
            value = lottieResult.value,
            error = lottieResult.error,
            isLoading = lottieResult.isLoading,
            isComplete = lottieResult.isComplete,
            isFailed = lottieResult.isComplete && lottieResult.error != null
        )
    } ?: KottieCompositionResult.error(IllegalStateException("Failed to create LottieCompositionSpec"))
}