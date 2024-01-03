package json_animation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource


@OptIn(ExperimentalResourceApi::class)
@SuppressLint("DiscouragedApi")
@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    fileName: Resource,
    speed: Float,
    iterations: Int,
    completed: () -> Unit
) {

    var animation by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        animation = fileName.readBytes().decodeToString()
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.JsonString(animation)
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

