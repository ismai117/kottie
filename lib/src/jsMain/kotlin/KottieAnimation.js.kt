import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color
) {

    SkiaAnimation(
        composition = composition,
        progress = { progress() },
        modifier = modifier,
        backgroundColor = backgroundColor
    )

}
