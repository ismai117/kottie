package text_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import kotlin.math.roundToInt


@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    text: String,
    speed: Float,
    iterations: Int,
    textStyle: TextStyle,
    completed: () -> Unit,
) {


    val time = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        time.animateTo(
            targetValue = 1f,
            animationSpec = repeatable(
                iterations = iterations,
                animation = tween(
                    durationMillis = (1000 / speed).roundToInt(),
                    easing = FastOutLinearInEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Text(
        text = text,
        style = textStyle,
        modifier = modifier
            .alpha(time.value)
    )

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

