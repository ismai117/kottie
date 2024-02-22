import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun App(
    modifier: Modifier = Modifier,
) {

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File("files/Animation.json")
    )

    val animationState by animateKottieCompositionAsState(
        composition = composition
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

        }
    }

}



