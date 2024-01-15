import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
expect fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float
)