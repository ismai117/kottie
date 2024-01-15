import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.skia.skottie.Animation
import kotlin.math.roundToInt


@Composable
fun animateSkiaCompositionAsState(
    composition: Animation?,
    speed: Float = 1f,
    iterations: Int = 1,
): State<SkiaAnimationState>{

    val animatable = remember { Animatable(initialValue = 0f) }

    val skiaAnimationState = remember { mutableStateOf(SkiaAnimationState()) }

    LaunchedEffect(
        composition,
        speed,
        iterations
    ) {
        when (val animation = composition) {
            null -> {}
            else -> {
                animatable.animateTo(
                    targetValue = animation.duration,
                    animationSpec = if (iterations == Int.MAX_VALUE) {
                        infiniteRepeatable(
                            animation = tween(
                                durationMillis = (animation.duration * 1000 / speed).roundToInt(),
                                easing = LinearEasing
                            ),
                            repeatMode = RepeatMode.Restart
                        )
                    } else {
                        repeatable(
                            iterations = iterations,
                            animation = tween(
                                durationMillis = (animation.duration * 1000 / speed).roundToInt(),
                                easing = LinearEasing
                            ),
                            repeatMode = RepeatMode.Restart
                        )
                    }
                )
            }
        }
    }

    LaunchedEffect(
        key1 = animatable.value
    ) {
        when (val animation = composition) {
            null -> {}
            else -> {
                skiaAnimationState.value = skiaAnimationState.value.copy(
                    composition = composition,
                    isPlaying = animatable.isRunning,
                    isCompleted = animatable.value > 0.0 && animatable.value == animation.duration,
                    progress = animatable.value,
                    duration = animation.duration,
                    iterations = iterations,
                    speed = speed
                )
            }
        }
    }

    return skiaAnimationState

}