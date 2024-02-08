package animateKottieCompositionAsState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cocoapods.lottie_ios.CompatibleAnimationView
import kotlinx.cinterop.ExperimentalForeignApi
import kottieAnimationState.KottieAnimationState
import lottie.animateLottieCompositionAsState.animateLottieCompositionAsState

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun animateKottieCompositionAsState(
    composition: Any?,
    speed: Float,
    iterations: Int,
    isPlaying: Boolean
): State<KottieAnimationState> {

    val kottieAnimationState = remember { mutableStateOf(KottieAnimationState()) }

    val animationState = animateLottieCompositionAsState(
        composition = composition as? CompatibleAnimationView,
        speed = speed,
        iterations = iterations,
        isPlaying = isPlaying
    )

    if (animationState.isPlaying){
        println("progress: ${animationState.progress}")
        println("duration: ${animationState.duration}")
    }

    LaunchedEffect(animationState.progress){
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