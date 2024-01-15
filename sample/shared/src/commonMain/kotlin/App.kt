import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
) {

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.Url("https://lottie.host/906091e9-9688-4810-9f6f-56505e9a45e4/Xano3sW7sH.json")
    )

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        speed = 1f,
        iterations = 2
    )


    MaterialTheme {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            KottieAnimation(
                composition = composition,
                progress = { animationState.progress },
                modifier = modifier.fillMaxSize(),
            )

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
        }
    }

}



//  fileName = resource("Animation.json")

//  url = "https://lottie.host/8df96fe9-2c73-4e68-bbb3-baa1f938cc83/rg7oq4lck2.json",

// url = "https://lottie.host/e3746a51-78df-4e2d-b4e3-8f6bffdbe069/wcMpvLW6qv.json"

// url = "https://lottie.host/906091e9-9688-4810-9f6f-56505e9a45e4/Xano3sW7sH.json",
expect fun getPlatformName(): String

