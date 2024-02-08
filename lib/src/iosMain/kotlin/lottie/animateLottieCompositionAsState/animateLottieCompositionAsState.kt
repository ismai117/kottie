package lottie.animateLottieCompositionAsState



import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cocoapods.lottie_ios.CompatibleAnimationView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.delay
import lottie.LottieAnimationState


@OptIn(ExperimentalForeignApi::class)
@Composable
fun animateLottieCompositionAsState(
    composition: CompatibleAnimationView?,
    isPlaying: Boolean = true,
    speed: Float = 1f,
    iterations: Int = 1
): LottieAnimationState {

    require(iterations > 0) { "Iterations must be a positive number ($iterations)." }
    require(speed.isFinite()) { "Speed must be a finite number. It is $speed." }

    val lottieAnimationState = remember { mutableStateOf(LottieAnimationState()) }

    val progress = remember { mutableStateOf(0.0f) }

    LaunchedEffect(
        composition,
        isPlaying,
        speed,
        iterations
    ) {
        when (val animation = composition) {
            null -> {}
            else -> {

                if (!isPlaying) return@LaunchedEffect

                animation.setLoopAnimationCount(iterations.toDouble())
                animation.setAnimationSpeed(speed.toDouble())
                animation.play()

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