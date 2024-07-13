import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.window.ComposeUIViewController

@OptIn(ExperimentalComposeApi::class)
fun MainViewController() = ComposeUIViewController { App() }