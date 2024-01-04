
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import url_animation.KottieAnimation
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import utils.KottieConstants

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        Column(
            Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
//           KottieAnimation(
//               fileName = resource("Animation.json"),
//               iterations = KottieConstants.IterateForever
//           )
//            KottieAnimation(
//                url = "https://lottie.host/906091e9-9688-4810-9f6f-56505e9a45e4/Xano3sW7sH.json",
//                iterations = KottieConstants.IterateForever
//            )
//            image_animation.KottieAnimation(
//                image = resource(""),
//                iterations = KottieConstants.IterateForever
//            )
        }
    }
}

expect fun getPlatformName(): String
