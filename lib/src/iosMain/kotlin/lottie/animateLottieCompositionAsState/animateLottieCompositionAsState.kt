package lottie.animateLottieCompositionAsState


import Lottie.AnimatedButton.Companion.setAnimationRepeatAutoreverses
import Lottie.CompatibleAnimation
import Lottie.CompatibleAnimationView
import Lottie.LottieAnimationView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.delay
import lottie.LottieAnimationState


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
                composition.setLoopAnimationCount(iterations.toDouble())
                composition.setAnimationSpeed(speed.toDouble())
                composition.play()

            }
        }
    }

    LaunchedEffect(
        composition?.realtimeAnimationProgress()?.toFloat(),
        isPlaying
    ){
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

