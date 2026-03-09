package kottie

import Lottie.CompatibleAnimationView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.delay

@OptIn(ExperimentalForeignApi::class)
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
    require(iterations > 0) { "Iterations must be a positive number ($iterations)." }
    require(speed.isFinite()) { "Speed must be a finite number. It is $speed." }

    val kottieAnimationState = remember { mutableStateOf(KottieAnimationState()) }
    var wasPlaying by remember { mutableStateOf(isPlaying) }
    val progress = remember { mutableStateOf(0f) }
    var currentIteration by remember { mutableStateOf(0) }

    val animationView = (composition as? KottieComposition.Success)?.composition as? CompatibleAnimationView

    LaunchedEffect(animationView, isPlaying, speed, iterations) {
        animationView ?: return@LaunchedEffect

        if (isPlaying && !wasPlaying && restartOnPlay) {
            animationView.setCurrentProgress(0.0)
        }

        wasPlaying = isPlaying
        if (!isPlaying) return@LaunchedEffect

        animationView.setRespectAnimationFrameRate(useCompositionFrameRate)
        animationView.setAnimationSpeed(speed.toDouble())

        if (reverseOnRepeat) {
            when {
                iterations == 1 -> {
                    animationView.playWithCompletion { completed ->
                        if (completed) {
                            animationView.playFromProgress(1.0, 0.0, null)
                        }
                    }
                }
                else -> {
                    fun loopForwardAndBackward() {
                        animationView.playFromProgress(0.0, 1.0) { forwardCompleted ->
                            if (forwardCompleted) {
                                animationView.playFromProgress(1.0, 0.0) { backwardCompleted ->
                                    if (backwardCompleted) {
                                        currentIteration++
                                        if (currentIteration < iterations || iterations == Kottie.IterateForever) {
                                            loopForwardAndBackward()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    loopForwardAndBackward()
                }
            }
        } else {
            animationView.setLoopAnimationCount(iterations.toDouble())
            animationView.play()
        }
    }

    LaunchedEffect(animationView?.realtimeAnimationProgress()?.toFloat(), isPlaying) {
        delay(100)
        progress.value = animationView?.realtimeAnimationProgress()?.toFloat() ?: 0f
    }

    LaunchedEffect(progress.value) {
        if (!isPlaying || animationView == null) return@LaunchedEffect

        kottieAnimationState.value = KottieAnimationState(
            progress = progress.value,
            isPlaying = animationView.isAnimationPlaying(),
            isCompleted = progress.value in 0.99f..1f,
            iteration = currentIteration,
            duration = animationView.duration().toFloat(),
            speed = speed
        )
    }

    return kottieAnimationState
}
