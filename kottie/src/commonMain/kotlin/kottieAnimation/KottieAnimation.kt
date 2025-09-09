package kottieAnimation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import contentScale.ContentScale

@Composable
internal expect fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color = Color.Transparent,
    contentScale: ContentScale = ContentScale.Fit,
    clipToCompositionBounds: Boolean = true,
)