import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kottie.Kottie
import kottie.KottieAnimation
import kottie.KottieComposition
import kottie.KottieCompositionSpec
import kottie.animateKottieCompositionAsState
import kottie.rememberKottieComposition
import kottie.sample.shared.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun App(modifier: Modifier = Modifier) {
    MaterialTheme {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Kottie Demo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            AnimationCard(title = "From File") {
                FileAnimation()
            }

            AnimationCard(title = "From URL") {
                UrlAnimation()
            }

            AnimationCard(title = "From JSON") {
                JsonAnimation()
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun FileAnimation() {
    var json by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        json = Res.readBytes("files/animation.json").decodeToString()
    }

    val composition = rememberKottieComposition(KottieCompositionSpec.File(json))
    AnimationWithState(composition)
}

@Composable
private fun UrlAnimation() {
    val composition = rememberKottieComposition(
        KottieCompositionSpec.Url("https://lottie.host/4cd47dc2-c1ae-4d46-b908-2df421fa26ca/PdsqGQXLt0.json")
    )
    AnimationWithState(composition)
}

@Composable
private fun JsonAnimation() {
    val json = """
    {
      "v": "5.7.4", "fr": 25, "ip": 0, "op": 50, "w": 100, "h": 100,
      "nm": "Square", "ddd": 0, "assets": [],
      "layers": [{
        "ddd": 0, "ind": 1, "ty": 4, "nm": "Square", "sr": 1,
        "ks": {
          "o": {"a": 0, "k": 100},
          "r": {"a": 1, "k": [{"t": 0, "s": [0]}, {"t": 49, "s": [360]}]},
          "p": {"a": 0, "k": [50, 50]},
          "a": {"a": 0, "k": [0, 0]},
          "s": {"a": 0, "k": [100, 100]}
        },
        "ao": 0,
        "shapes": [
          {"ty": "rc", "d": 1, "s": {"a": 0, "k": [40, 40]}, "p": {"a": 0, "k": [0, 0]}, "r": {"a": 0, "k": 5}},
          {"ty": "fl", "c": {"a": 0, "k": [0.2, 0.6, 1, 1]}, "o": {"a": 0, "k": 100}}
        ],
        "ip": 0, "op": 50, "st": 0
      }],
      "markers": []
    }
    """.trimIndent()

    val composition = rememberKottieComposition(KottieCompositionSpec.JsonString(json))
    AnimationWithState(composition)
}

@Composable
private fun AnimationWithState(composition: KottieComposition) {
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = Kottie.IterateForever
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(70.dp)
        ) {
            when (composition) {
                is KottieComposition.Loading -> {
                    Text("...", style = MaterialTheme.typography.titleLarge)
                    Text("Loading", style = MaterialTheme.typography.bodySmall)
                }
                is KottieComposition.Success -> {
                    Text("OK", style = MaterialTheme.typography.titleLarge, color = Color(0xFF4CAF50))
                    Text("Ready", style = MaterialTheme.typography.bodySmall)
                }
                is KottieComposition.Failure -> {
                    Text("!", style = MaterialTheme.typography.titleLarge, color = Color(0xFFF44336))
                    Text("Error", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Animation
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            when (composition) {
                is KottieComposition.Loading -> CircularProgressIndicator()
                is KottieComposition.Success -> {
                    KottieAnimation(
                        composition = composition,
                        progress = { animationState.progress },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is KottieComposition.Failure -> {
                    Text("X", style = MaterialTheme.typography.headlineLarge, color = Color(0xFFF44336))
                }
            }
        }

        // Error details
        if (composition is KottieComposition.Failure) {
            Text(
                text = composition.error.message ?: "Unknown error",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFF44336),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AnimationCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            content()
        }
    }
}
