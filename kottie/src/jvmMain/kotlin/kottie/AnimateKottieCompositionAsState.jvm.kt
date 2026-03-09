package kottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kottie.internal.animateSkiaCompositionAsState

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
    return animateSkiaCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        restartOnPlay = restartOnPlay,
        reverseOnRepeat = reverseOnRepeat,
        speed = speed,
        iterations = iterations,
        useCompositionFrameRate = useCompositionFrameRate
    )
}
