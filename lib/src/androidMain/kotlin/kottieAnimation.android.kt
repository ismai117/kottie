import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation



@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: androidx.compose.ui.graphics.Color
) {

    LottieAnimation(
        composition = composition as? LottieComposition,
        progress = { progress() },
        modifier = modifier
            .background(backgroundColor)
    )

}