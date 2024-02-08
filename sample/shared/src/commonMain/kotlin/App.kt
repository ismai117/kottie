import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import animateKottieCompositionAsState.animateKottieCompositionAsState
import kottieComposition.KottieCompositionSpec
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import utils.KottieConstants


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
) {

    var playing by remember { mutableStateOf(false) }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.Url("https://lottie.host/972ae0a6-d541-408f-ba32-25f5a0109c39/lFxAzvdRl8.json")
    )

    // Url("https://lottie.host/972ae0a6-d541-408f-ba32-25f5a0109c39/lFxAzvdRl8.json")
    // KottieCompositionSpec.File(resource("people.json"))

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        speed = 1f,
        iterations = KottieConstants.IterateForever,
        isPlaying = playing
    )

    MaterialTheme {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

//            if (animationState.isCompleted) {
//                Text("Animation Completed")
//            }


            if (playing){
                KottieAnimation(
                    composition = composition,
                    progress = { animationState.progress },
                    modifier = modifier.size(300.dp),
                )
            }

            Button(
                onClick = {
                    playing = true
                }
            ){
                Text("Play")
            }

        }
    }


    LaunchedEffect(
        key1 = animationState.isPlaying
    ) {
        if (animationState.isPlaying) {
            println("Animation Playing")
        }
        if (animationState.isCompleted) {
            println("Animation Completed")
            playing = false
        }
    }

}


//  fileName = resource("Animation.json")

//  url = "https://lottie.host/8df96fe9-2c73-4e68-bbb3-baa1f938cc83/rg7oq4lck2.json",

// url = "https://lottie.host/e3746a51-78df-4e2d-b4e3-8f6bffdbe069/wcMpvLW6qv.json"

// url = "https://lottie.host/906091e9-9688-4810-9f6f-56505e9a45e4/Xano3sW7sH.json",

expect fun getPlatformName(): String

