package kottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import kottieAnimationState.KottieAnimationState


@Composable
actual fun animateKottieCompositionAsState(
    composition: Any?,
    isPlaying: Boolean,
    restartOnPlay: Boolean,
    reverseOnRepeat: Boolean ,
    speed: Float ,
    iterations: Int,
    useCompositionFrameRate: Boolean,
): State<KottieAnimationState> {

    val kottieAnimationState = remember { mutableStateOf(KottieAnimationState()) }

    val animationState = animateLottieCompositionAsState(
        composition = composition as? LottieComposition,
        isPlaying = isPlaying,
        restartOnPlay = restartOnPlay,
        reverseOnRepeat = reverseOnRepeat,
        speed = speed,
        iterations = iterations,
        useCompositionFrameRate = useCompositionFrameRate
    )

    println("height: ${animationState.composition?.bounds}")

    LaunchedEffect(
        animationState.progress
    ) {
        kottieAnimationState.value = kottieAnimationState.value.copy(
            composition = animationState.composition,
            isPlaying = animationState.isPlaying,
            reverseOnRepeat = animationState.reverseOnRepeat,
            isCompleted = animationState.progress in 0.99..1.0,
            progress = animationState.progress,
            duration = animationState.composition?.duration?.coerceIn(0.0f, 1.0f) ?: 0.0f,
            iterations = animationState.iterations,
            speed = animationState.speed
        )
    }

    return kottieAnimationState
}