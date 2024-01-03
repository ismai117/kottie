package url_animation

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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.Data
import org.jetbrains.skia.Rect
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController
import java.net.URL
import kotlin.math.roundToInt


@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    url: String,
    speed: Float,
    iterations: Int,
    completed: () -> Unit
) {

    var animationState by remember { mutableStateOf<Animation?>(null) }

    LaunchedEffect(Unit) {
        animationState = Animation.makeFromData(
            Data.makeFromBytes(URL(url).readBytes())
        )
    }

    val time = remember { Animatable(0f) }

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

//            println("progress: ${time.value}")

            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .drawAnimationOnCanvas(
                        animation = animation,
                        time = time.value,
                        invalidationController = invalidationController
                    )
            ){}

            LaunchedEffect(
                key1 = time.isRunning,
            ){
                if (time.isRunning){
                    println("animation running!!")
                }else{
                    if (iterations == 1){
                        println("animation completed!!")
                        completed.invoke()
                    }
                }
            }

        }
    }


}

fun Modifier.drawAnimationOnCanvas(
    animation: Animation,
    time: Float,
    invalidationController: InvalidationController
): Modifier = this then drawWithContent {
    drawIntoCanvas { canvas ->
        animation.seekFrameTime(time, invalidationController)
        animation.render(
            canvas = canvas.nativeCanvas,
            dst = Rect.makeWH(size.width, size.height)
        )
    }
}
