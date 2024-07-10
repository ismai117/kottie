package kottieComposition

import Lottie.CompatibleAnimationView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kottieAnimationState.KottieAnimationState
import lottie.animateLottieCompositionAsState.animateLottieCompositionAsState

@OptIn(ExperimentalForeignApi::class)
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
        composition = composition as? CompatibleAnimationView,
        isPlaying = isPlaying,
        restartOnPlay = restartOnPlay,
        reverseOnRepeat = reverseOnRepeat,
        speed = speed,
        iterations = iterations,
        useCompositionFrameRate = useCompositionFrameRate
    )
    LaunchedEffect(
        animationState.progress
    ){
        kottieAnimationState.value = kottieAnimationState.value.copy(
            composition = animationState.composition,
            isPlaying = animationState.isPlaying,
            isCompleted = animationState.isCompleted,
            progress = animationState.progress,
            duration = animationState.duration,
            iterations = animationState.iterations,
            speed = animationState.speed
        )
    }
    return kottieAnimationState
}