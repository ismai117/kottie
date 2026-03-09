package kottie.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kottie.ContentScale

/**
 * Internal expect function for rendering animations.
 * Platform implementations handle the actual rendering.
 */
@Composable
internal expect fun KottieAnimationInternal(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color,
    contentScale: ContentScale,
    clipToCompositionBounds: Boolean,
)
