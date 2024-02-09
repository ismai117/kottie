import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kottieAnimationState.KottieAnimationState


@Composable
expect fun animateKottieCompositionAsState(
    composition: Any?,
    speed: Float = 1f,
    iterations: Int = 1,
    isPlaying: Boolean = true
): State<KottieAnimationState>