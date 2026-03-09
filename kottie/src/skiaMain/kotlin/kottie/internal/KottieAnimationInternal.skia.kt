package kottie.internal

import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import kottie.ContentScale
import org.jetbrains.skia.Rect
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController

@Composable
internal actual fun KottieAnimationInternal(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color,
    contentScale: ContentScale,
    clipToCompositionBounds: Boolean
) {
    val animation = composition as? Animation ?: return
    val invalidationController = remember { InvalidationController() }

    Surface(
        modifier = modifier.drawAnimationOnCanvas(
            animation = animation,
            time = progress(),
            invalidationController = invalidationController
        ),
        color = backgroundColor
    ) {}
}

private fun Modifier.drawAnimationOnCanvas(
    animation: Animation,
    time: Float,
    invalidationController: InvalidationController
): Modifier = this then drawWithContent {
    drawContent()
    drawIntoCanvas { canvas ->
        animation.seekFrameTime(time, invalidationController)
        animation.render(
            canvas = canvas.nativeCanvas,
            dst = Rect.makeWH(size.width, size.height)
        )
    }
}
