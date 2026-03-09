package kottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.animateLottieCompositionAsState

@Composable
actual fun animateKottieCompositionAsState(
    composition: KottieComposition,
    isPlaying: Boolean,
    restartOnPlay: Boolean,
    reverseOnRepeat: Boolean,
    speed: Float,
    iterations: Int,
    useCompositionFrameRate: Boolean,
): State<KottieAnimationState> {
    val kottieAnimationState = remember { mutableStateOf(KottieAnimationState()) }

    val lottieComposition = (composition as? KottieComposition.Success)?.composition as? LottieComposition

    val animationState = animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isPlaying && composition is KottieComposition.Success,
        restartOnPlay = restartOnPlay,
        reverseOnRepeat = reverseOnRepeat,
        speed = speed,
        iterations = iterations,
        useCompositionFrameRate = useCompositionFrameRate
    )

    LaunchedEffect(animationState.progress) {
        kottieAnimationState.value = KottieAnimationState(
            progress = animationState.progress,
            isPlaying = animationState.isPlaying,
            isCompleted = animationState.progress in 0.99..1.0,
            iteration = animationState.iterations,
            duration = animationState.composition?.duration ?: 0f,
            speed = animationState.speed
        )
    }

    return kottieAnimationState
}
