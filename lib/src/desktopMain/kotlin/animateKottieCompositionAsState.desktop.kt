import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.skia.skottie.Animation

@Composable
actual fun animateKottieCompositionAsState(
    composition: Any?,
    speed: Float,
    iterations: Int
): State<KottieAnimationState> {

    val kottieAnimationState = remember { mutableStateOf(KottieAnimationState()) }

    val animationState by animateSkiaCompositionAsState(
        composition = composition as? Animation,
        speed = speed,
        iterations = iterations
    )

    LaunchedEffect(
        animationState.progress
    ) {
        kottieAnimationState.value = kottieAnimationState.value.copy(
            composition = animationState.composition,
            isPlaying = animationState.isPlaying,
            isCompleted = animationState.progress > 0.0 && animationState.progress == animationState.duration,
            progress = animationState.progress,
            duration = animationState.duration,
            iterations = animationState.iterations,
            speed = animationState.speed
        )
    }

    return kottieAnimationState

}