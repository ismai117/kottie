import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition


@Composable
fun App(
    modifier: Modifier = Modifier,
) {


    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File("files/Animation.json")
    )

    var isPlaying by remember { mutableStateOf(false) }

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = 1,
        isPlaying = isPlaying
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
                modifier = modifier
                    .size(300.dp)
            )

            Button(
                onClick = {
                    isPlaying = true
                }
            ){
                Text(
                    text = when(isPlaying){
                        true -> "Playing..."
                        false -> "Play"
                    }
                )
            }

        }
    }

    LaunchedEffect(animationState.isPlaying){
        if (animationState.isCompleted){
            isPlaying = false
        }
    }

}



