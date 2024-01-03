package url_animation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    url: String,
    speed: Float,
    iterations: Int,
    completed: () -> Unit
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(url)
    )

    val animationState = animateLottieCompositionAsState(
        composition = composition,
        speed = speed,
        iterations = iterations
    )

    LaunchedEffect(key1 = animationState.progress){
        if (animationState.progress == 1f){
            completed()
        }
    }

    LottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = Modifier.fillMaxSize(),
    )

}