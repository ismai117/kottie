package kottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kottieAnimationState.KottieAnimationState


@Composable
expect fun animateKottieCompositionAsState(
    composition: KottieCompositionResult,
    isPlaying: Boolean = true,
    restartOnPlay: Boolean = true,
    reverseOnRepeat: Boolean = false,
    speed: Float = 1f,
    iterations: Int = 1,
    useCompositionFrameRate: Boolean = false
): State<KottieAnimationState>