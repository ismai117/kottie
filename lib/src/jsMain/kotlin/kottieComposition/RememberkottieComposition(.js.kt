package kottieComposition

import SkiaCompositionSpec
import androidx.compose.runtime.Composable
import rememberSkiaComposition

@Composable
actual fun rememberKottieComposition(
    spec: KottieCompositionSpec
): Any? {
    return when(spec){
        is KottieCompositionSpec.File -> {
            (kottieComposition(spec = spec) as? SkiaCompositionSpec)?.let {
                rememberSkiaComposition(it)
            }
        }
        is KottieCompositionSpec.JsonString -> {
            (kottieComposition(spec = spec) as? SkiaCompositionSpec)?.let {
                rememberSkiaComposition(it)
            }
        }
        is KottieCompositionSpec.Url -> {
            (kottieComposition(spec = spec) as? SkiaCompositionSpec)?.let {
                rememberSkiaComposition(it)
            }
        }
    }
}