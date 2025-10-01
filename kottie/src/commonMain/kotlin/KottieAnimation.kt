import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import contentScale.ContentScale
import kottieComposition.KottieCompositionResult


@Composable
fun KottieAnimation(
    modifier: Modifier = Modifier,
    composition: KottieCompositionResult,
    progress: () -> Float,
    backgroundColor: Color = Color.Transparent,
    contentScale: ContentScale = ContentScale.Fit,
    clipToCompositionBounds: Boolean = true,
    minWidth: Dp = 140.dp,
    minHeight: Dp = 140.dp,
) {
    kottieAnimation.KottieAnimation(
        modifier = modifier.sizeIn(minWidth = minWidth, minHeight = minHeight),
        composition = composition.value,
        progress = progress,
        backgroundColor = backgroundColor,
        contentScale = contentScale,
        clipToCompositionBounds = clipToCompositionBounds
    )
}