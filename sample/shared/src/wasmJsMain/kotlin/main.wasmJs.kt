import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "wasmJs"

@Composable fun MainView() = App()
