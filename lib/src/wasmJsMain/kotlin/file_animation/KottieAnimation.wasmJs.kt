package json_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource
import org.jetbrains.skia.Data
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController
import url_animation.drawAnimationOnCanvas
import kotlin.math.roundToInt


@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    fileName: Resource,
    speed: Float,
    iterations: Int,
    isPlaying: isPlaying,
    completed: completed
) {

    var animationState by remember { mutableStateOf<Animation?>(null) }

    LaunchedEffect(Unit) {
        animationState = Animation.makeFromData(
            Data.makeFromBytes(fileName.readBytes())
        )
    }

    val time = remember { Animatable(initialValue = 0f) }

    when (val animation = animationState) {
        null -> {}
        else -> {
            LaunchedEffect(Unit) {
                time.animateTo(
                    targetValue = animation.duration,
                    animationSpec = repeatable(
                        iterations = iterations,
                        animation = tween(
                            durationMillis = (animation.duration * 1000 / speed).roundToInt(),
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }

            val invalidationController = remember { InvalidationController() }

            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .drawAnimationOnCanvas(
                        animation = animation,
                        time = time.value,
                        invalidationController = invalidationController
                    )
            ){}

            LaunchedEffect(key1 = time.value){
                if (time.value == animation.duration){
                    completed(true)
                }
            }

            LaunchedEffect(key1 = time.isRunning){
                isPlaying(time.isRunning)
            }

        }
    }


}
