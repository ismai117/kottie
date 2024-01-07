

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import url_animation.KottieAnimation
import utils.KottieConstants



@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier
) {
    MaterialTheme {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            KottieAnimation(
                url = "https://lottie.host/906091e9-9688-4810-9f6f-56505e9a45e4/Xano3sW7sH.json",
                iterations = KottieConstants.IterateForever
            )
        }
    }
}

//url = "https://lottie.host/906091e9-9688-4810-9f6f-56505e9a45e4/Xano3sW7sH.json",

expect fun getPlatformName(): String
