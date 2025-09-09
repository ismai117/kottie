package kottieAnimation

import SkiaAnimation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import contentScale.ContentScale


@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color,
    contentScale: ContentScale,
    clipToCompositionBounds: Boolean
) {
    SkiaAnimation(
        composition = composition,
        progress = { progress() },
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentScale = contentScale,
        clipToCompositionBounds = clipToCompositionBounds
    )
}
