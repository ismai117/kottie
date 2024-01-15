
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State


@Composable
expect fun animateKottieCompositionAsState(
    composition: Any?,
    speed: Float = 1f,
    iterations: Int = 1,
): State<KottieAnimationState>