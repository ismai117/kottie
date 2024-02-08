package lottie.animateLottieCompositionAsState


import androidx.compose.foundation.MutatorMutex
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cocoapods.lottie_ios.CompatibleAnimationView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import lottie.LottieAnimationState
import platform.CoreGraphics.CGFloat
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext


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

    LaunchedEffect(
        composition,
        isPlaying,
        speed,
        iterations
    ) {

        if (composition == null) {
            return@LaunchedEffect
        }

        composition.setLoopAnimationCount(iterations.toDouble())
        composition.setAnimationSpeed(speed.toDouble())
        composition.play()

    }

    LaunchedEffect(
        key1 = composition?.realtimeAnimationProgress()?.toFloat()?.coerceIn(0.0f, 1.0f)
    ) {

        if (composition == null) {
            return@LaunchedEffect
        }

        lottieAnimationState.value = lottieAnimationState.value.copy(
            composition = composition,
            progress = composition.realtimeAnimationProgress().toFloat().coerceIn(0.0f, 1.0f),
            duration = composition.duration().toFloat().coerceIn(0.0f, 1.0f),
            isPlaying = composition.isAnimationPlaying(),
            isCompleted = composition.realtimeAnimationProgress().toFloat().coerceIn(0.0f, 1.0f) in 0.99..1.0,
            iterations = iterations,
            speed = speed
        )

    }

    return lottieAnimationState.value

}

