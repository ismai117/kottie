import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.cinterop.ExperimentalForeignApi
import lottie.LottieAnimation


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float
) {

    LottieAnimation(
        composition = composition,
        progress = { progress() },
        modifier = modifier
    )

}


