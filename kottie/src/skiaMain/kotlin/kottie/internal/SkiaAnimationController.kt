package kottie.internal

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kottie.Kottie
import kottie.KottieAnimationState
import kottie.KottieComposition
import org.jetbrains.skia.skottie.Animation
import kotlin.math.roundToInt

@Composable
internal fun animateSkiaCompositionAsState(
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

    val animatable = remember { Animatable(initialValue = 0f) }
    var wasPlaying by remember { mutableStateOf(isPlaying) }
    val kottieAnimationState = remember { mutableStateOf(KottieAnimationState()) }

    val animation = (composition as? KottieComposition.Success)?.composition as? Animation

    LaunchedEffect(animation, isPlaying, speed, iterations) {
        animation ?: return@LaunchedEffect

        if (isPlaying && !wasPlaying && restartOnPlay) {
            animatable.snapTo(0f)
        }
        wasPlaying = isPlaying

        if (!isPlaying) return@LaunchedEffect

        val durationMs = (animation.duration * 1000 / speed).roundToInt()

        animatable.animateTo(
            targetValue = animation.duration,
            animationSpec = if (iterations == Kottie.IterateForever) {
                infiniteRepeatable(
                    animation = tween(durationMillis = durationMs, easing = LinearEasing),
                    repeatMode = if (reverseOnRepeat) RepeatMode.Reverse else RepeatMode.Restart
                )
            } else {
                repeatable(
                    iterations = iterations,
                    animation = tween(durationMillis = durationMs, easing = LinearEasing),
                    repeatMode = if (reverseOnRepeat) RepeatMode.Reverse else RepeatMode.Restart
                )
            }
        )
    }

    LaunchedEffect(animatable.value) {
        animation ?: return@LaunchedEffect

        kottieAnimationState.value = KottieAnimationState(
            progress = animatable.value,
            isPlaying = animatable.isRunning,
            isCompleted = animatable.value > 0f && animatable.value >= animation.duration,
            iteration = 0, // Skia doesn't expose current iteration
            duration = animation.duration,
            speed = speed
        )
    }

    return kottieAnimationState
}
