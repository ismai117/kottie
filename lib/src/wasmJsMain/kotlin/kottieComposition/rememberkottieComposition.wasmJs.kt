package kottieComposition

import androidx.compose.runtime.Composable
import skiaComposition.SkiaCompositionSpec
import skiaComposition.rememberSkiaComposition

@Composable
actual fun rememberKottieComposition(
    spec: KottieCompositionSpec
): KottieCompositionResult {
    return (kottieComposition(spec = spec) as? SkiaCompositionSpec)?.let {
        rememberSkiaComposition(it)
    } ?: KottieCompositionResult.error(IllegalStateException("Failed to create SkiaCompositionSpec"))
}