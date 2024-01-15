import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.animateLottieCompositionAsState


@Composable
actual fun animateKottieCompositionAsState(
    composition: Any?,
    speed: Float,
    iterations: Int
): State<KottieAnimationState> {

    val kottieAnimationState = remember { mutableStateOf(KottieAnimationState()) }

    val animationState = animateLottieCompositionAsState(
        composition = composition as? LottieComposition,
        speed = speed,
        iterations = iterations
    )

    LaunchedEffect(
        animationState.progress
    ) {
        kottieAnimationState.value = kottieAnimationState.value.copy(
            composition = animationState.composition,
            isPlaying = animationState.isPlaying,
            isCompleted = animationState.progress> 0.0 && animationState.progress == animationState.composition?.duration?.coerceIn(0.0f, 1.0f),
            progress = animationState.progress,
            duration = animationState.composition?.duration?.coerceIn(0.0f, 1.0f) ?: 0.0f,
            iterations = animationState.iterations,
            speed = animationState.speed
        )
    }

    return kottieAnimationState

}