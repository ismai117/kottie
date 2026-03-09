package kottie.internal

import Lottie.CompatibleAnimationView
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import kottie.ContentScale
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode

@OptIn(ExperimentalForeignApi::class)
@Composable
internal actual fun KottieAnimationInternal(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color,
    contentScale: ContentScale,
    clipToCompositionBounds: Boolean
) {
    val animationView = composition as? CompatibleAnimationView ?: return

    val scaleType = when (contentScale) {
        ContentScale.Fit -> UIViewContentMode.UIViewContentModeScaleAspectFit
        ContentScale.Crop -> UIViewContentMode.UIViewContentModeScaleAspectFill
        ContentScale.FillBounds -> UIViewContentMode.UIViewContentModeScaleToFill
    }

    androidx.compose.ui.viewinterop.UIKitView(
        factory = {
            UIView().apply {
                this.backgroundColor = UIColor.clearColor
                this.tintColor = UIColor.clearColor
                this.clipsToBounds = clipToCompositionBounds
            }
        },
        modifier = modifier.background(backgroundColor),
        update = { view ->
            animationView.translatesAutoresizingMaskIntoConstraints = false
            animationView.contentMode = scaleType
            animationView.clipsToBounds = clipToCompositionBounds

            view.addSubview(animationView)

            NSLayoutConstraint.activateConstraints(
                listOf(
                    animationView.widthAnchor.constraintEqualToAnchor(view.widthAnchor),
                    animationView.heightAnchor.constraintEqualToAnchor(view.heightAnchor)
                )
            )
        }
    )
}
