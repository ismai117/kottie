import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float
) {

    SkiaAnimation(
        composition = composition,
        progress = { progress() },
        modifier = modifier
    )

}
