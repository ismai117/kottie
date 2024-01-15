import androidx.compose.runtime.Composable


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