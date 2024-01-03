package text_animation

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle



@Composable
expect fun KottieAnimation(
    modifier: Modifier = Modifier,
    text: String,
    speed: Float = 1f,
    iterations: Int = 1,
    textStyle: TextStyle = LocalTextStyle.current,
    completed: () -> Unit = {}
)


