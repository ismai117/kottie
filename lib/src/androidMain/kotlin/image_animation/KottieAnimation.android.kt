package image_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageBitmap
import kotlin.math.roundToInt

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    image: Resource,
    speed: Float,
    iterations: Int,
    completed: () -> Unit
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

    val value = image.rememberImageBitmap()

    Image(
        bitmap = value.orEmpty(),
        contentDescription = "",
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