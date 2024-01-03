
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import json_animation.KottieAnimation
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
           KottieAnimation(
               fileName = resource("Animation.json"),
               iterations = KottieConstants.IterateForever
           )
        }
    }
}

expect fun getPlatformName(): String
