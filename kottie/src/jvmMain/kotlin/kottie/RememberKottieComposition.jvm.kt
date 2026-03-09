package kottie

import androidx.compose.runtime.Composable
import kottie.internal.rememberSkiaComposition

@Composable
actual fun rememberKottieComposition(spec: KottieCompositionSpec): KottieComposition {
    return rememberSkiaComposition(spec)
}
