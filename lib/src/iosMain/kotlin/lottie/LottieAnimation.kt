package lottie


import Lottie.CompatibleAnimationView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.interop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode


@OptIn(ExperimentalForeignApi::class)
@Composable
fun LottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color
) {
    when (composition as? CompatibleAnimationView) {
        null -> {}
        else -> {
            UIKitView(
                modifier = modifier,
                factory = {
                    UIView(frame = CGRectMake(0.0, 0.0, 0.0, 0.0)).apply {
                        this.backgroundColor = UIColor.clearColor
                        opaque = false
                        clipsToBounds = true
                    }
                },
                background = backgroundColor,
                update = {
                    composition.translatesAutoresizingMaskIntoConstraints = false
                    composition.backgroundColor = UIColor.clearColor
                    composition.opaque = false
                    it.addSubview(composition)
                    it.opaque = false
                    NSLayoutConstraint.activateConstraints(
                        listOf(
                            composition.widthAnchor.constraintEqualToAnchor(it.widthAnchor),
                            composition.heightAnchor.constraintEqualToAnchor(it.heightAnchor)
                        )
                    )
                    composition.setContentMode(UIViewContentMode.UIViewContentModeScaleAspectFit)
                }
            )
        }
    }
}

