import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kottie.sample.shared.generated.resources.Res
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import utils.KottieConstants

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
) {
    MaterialTheme {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "API usage",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            ExampleCard(
                title = "1. File Loading",
                content = { FileLoadingExample() }
            )

            ExampleCard(
                title = "2. URL Loading",
                content = { UrlLoadingExample() }
            )
            
            ExampleCard(
                title = "3. Valid JSON String",
                content = { ValidJsonExample() }
            )
        }
    }
}

@Composable
fun AnimationExample(
    composition: kottieComposition.KottieCompositionResult,
    reverseOnRepeat: Boolean = false,
    errorMessage: String = "Error",
    description: String? = null,
    showErrorDetails: Boolean = false,
    customFailedContent: (@Composable () -> Unit)? = null
) {
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = KottieConstants.IterateForever,
        reverseOnRepeat = reverseOnRepeat
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusIndicator(composition)
        
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                composition.isLoading -> {
                    CircularProgressIndicator()
                }
                composition.isSuccess -> {
                    KottieAnimation(
                        composition = composition,
                        progress = { animationState.progress },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composition.isFailed -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "❌",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        
        // Custom content for different states
        when {
            composition.isFailed && customFailedContent != null -> {
                customFailedContent()
            }
            composition.isFailed && showErrorDetails -> {
                Column {
                    Text(
                        text = "Error Details:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = composition.error?.message ?: "Unknown error",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red
                    )
                }
            }
            description != null -> {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FileLoadingExample() {
    var animation by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/animation.json").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation)
    )

    AnimationExample(
        composition = composition,
        reverseOnRepeat = true
    )
}

@Composable
fun UrlLoadingExample() {
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.Url("https://assets3.lottiefiles.com/packages/lf20_V9t630.json")
    )

    AnimationExample(
        composition = composition,
        errorMessage = "Network Error"
    )
}

@Composable
fun ValidJsonExample() {
    val validLottieJson = """
    {
      "v": "5.7.4",
      "fr": 25,
      "ip": 0,
      "op": 50,
      "w": 100,
      "h": 100,
      "nm": "Simple Square",
      "ddd": 0,
      "assets": [],
      "layers": [
        {
          "ddd": 0,
          "ind": 1,
          "ty": 4,
          "nm": "Square",
          "sr": 1,
          "ks": {
            "o": {"a": 0, "k": 100},
            "r": {"a": 1, "k": [
              {"t": 0, "s": [0]},
              {"t": 49, "s": [360]}
            ]},
            "p": {"a": 0, "k": [50, 50]},
            "a": {"a": 0, "k": [0, 0]},
            "s": {"a": 0, "k": [100, 100]}
          },
          "ao": 0,
          "shapes": [
            {
              "ty": "rc",
              "d": 1,
              "s": {"a": 0, "k": [40, 40]},
              "p": {"a": 0, "k": [0, 0]},
              "r": {"a": 0, "k": 5}
            },
            {
              "ty": "fl",
              "c": {"a": 0, "k": [0.2, 0.6, 1, 1]},
              "o": {"a": 0, "k": 100}
            }
          ],
          "ip": 0,
          "op": 50,
          "st": 0
        }
      ],
      "markers": []
    }
    """.trimIndent()

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.JsonString(validLottieJson)
    )

    AnimationExample(
        composition = composition,
        description = "Simple rotating blue square animation from JSON string"
    )
}


@Composable
fun StatusIndicator(composition: kottieComposition.KottieCompositionResult) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        when {
            composition.isLoading -> {
                Text(
                    text = "🟡",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Loading",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            composition.isSuccess -> {
                Text(
                    text = "🟢",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Success",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            composition.isFailed -> {
                Text(
                    text = "🔴",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Failed",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ExampleCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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



