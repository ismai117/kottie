import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import lottie.LottieAnimation


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color
) {

    LottieAnimation(
        composition = composition,
        progress = { progress() },
        modifier = modifier,
        backgroundColor = backgroundColor
    )

}


