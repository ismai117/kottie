package url_animation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
expect fun KottieAnimation(
    modifier: Modifier = Modifier,
    url: String,
    speed: Float = 1f,
    iterations: Int = 1,
    completed: () -> Unit = {}
)


