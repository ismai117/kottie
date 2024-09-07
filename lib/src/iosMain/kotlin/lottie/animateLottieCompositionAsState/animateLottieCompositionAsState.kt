package lottie.animateLottieCompositionAsState


import Lottie.CompatibleAnimationView
import Lottie.CompatibleAnimationView.Companion.setAnimationRepeatAutoreverses
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.delay
import lottie.LottieAnimationState
import platform.darwin.nil
import utils.KottieConstants


@OptIn(ExperimentalForeignApi::class)
@Composable
fun animateLottieCompositionAsState(
    composition: CompatibleAnimationView?,
    isPlaying: Boolean = true,
    restartOnPlay: Boolean = true,
    reverseOnRepeat: Boolean = false,
    speed: Float = 1f,
    iterations: Int = 1,
    useCompositionFrameRate: Boolean = false,
): LottieAnimationState {

    require(iterations > 0) { "Iterations must be a positive number ($iterations)." }
    require(speed.isFinite()) { "Speed must be a finite number. It is $speed." }

    val lottieAnimationState = remember { mutableStateOf(LottieAnimationState()) }
    var wasPlaying by remember { mutableStateOf(isPlaying) }

    val progress = remember { mutableStateOf(0.0f) }

    var currentIteration by remember { mutableStateOf(0) }

    LaunchedEffect(
        composition,
        isPlaying,
        speed,
        iterations
    ) {
        when (composition) {
            null -> {}
            else -> {

                if (isPlaying && !wasPlaying && restartOnPlay) {
                    composition.setCurrentProgress(0.0)
                }

                wasPlaying = isPlaying
                if (!isPlaying) return@LaunchedEffect

                composition.setRespectAnimationFrameRate(useCompositionFrameRate)
                composition.setAnimationSpeed(speed.toDouble())

                if (reverseOnRepeat){
                    when {
                        iterations == 1 -> {
                            composition.playWithCompletion { completed ->
                                if (completed){
                                    composition.playFromProgress(1.0, 0.0, null)
                                }
                            }
                        }
                        else -> {

                            fun loopForwardAndBackward() {
                                composition.playFromProgress(0.0, 1.0, completion = { forwardCompleted ->
                                    if (forwardCompleted) {
                                        composition.playFromProgress(1.0, 0.0, completion = { backwardCompleted ->
                                            if (backwardCompleted) {
                                                currentIteration++
                                                if (currentIteration < iterations || iterations == KottieConstants.IterateForever){
                                                    loopForwardAndBackward()
                                                }
                                            }
                                        })
                                    }
                                })
                            }

                            loopForwardAndBackward()

                        }
                    }
                } else {
                    composition.setLoopAnimationCount(iterations.toDouble())
                    composition.play()
                }

            }

        }
    }

    LaunchedEffect(
        composition?.realtimeAnimationProgress()?.toFloat(),
        isPlaying
    ) {
        delay(100)
        progress.value = composition?.realtimeAnimationProgress()?.toFloat() ?: 0.0f
    }

    LaunchedEffect(
        key1 = progress.value
    ) {

        if (!isPlaying || composition == null) {
            return@LaunchedEffect
        }

        lottieAnimationState.value = lottieAnimationState.value.copy(
            composition = composition,
            isPlaying = composition.isAnimationPlaying(),
            isCompleted = progress.value in 0.99..1.0,
            progress = progress.value,
            duration = composition.duration().toFloat().coerceIn(0.0f, 1.0f),
            iterations = iterations,
            speed = speed
        )

    }

    return lottieAnimationState.value

}