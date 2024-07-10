import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import contentScale.ContentScale
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController


@Composable
internal fun SkiaAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color,
    contentScale: ContentScale
) {
    val invalidationController = remember { InvalidationController() }
    when (val animation = composition as? Animation) {
        null -> {}
        else -> {
            Surface(
                modifier = modifier
                    .background(backgroundColor)
                    .drawAnimationOnCanvas(
                        animation = animation,
                        time = progress(),
                        invalidationController = invalidationController
                    )
            ) {}
        }
    }
}