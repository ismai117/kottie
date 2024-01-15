import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.Rect
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController


internal fun Modifier.drawAnimationOnCanvas(
    animation: Animation?,
    time: Float,
    invalidationController: InvalidationController
): Modifier = this then drawWithContent {
    drawIntoCanvas { canvas ->
        animation?.seekFrameTime(time, invalidationController)
        animation?.render(
            canvas = canvas.nativeCanvas,
            dst = Rect.makeWH(size.width, size.height)
        )
    }
}
