package lottie


import Lottie.CompatibleAnimationView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIColor
import platform.UIKit.UIView


@OptIn(ExperimentalForeignApi::class)
@Composable
fun LottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color,
){
    when (composition as? CompatibleAnimationView) {
        null -> {}
        else -> {
            UIKitView(
                modifier = modifier,
                factory = {
                    UIView().apply {
                        this.backgroundColor = UIColor.clearColor
                        this.opaque = false
                        this.setClipsToBounds(true)
                    }
                },
                background = backgroundColor,
                update = {
                    composition.translatesAutoresizingMaskIntoConstraints = false
                    it.addSubview(composition)
                    it.opaque = true
                    NSLayoutConstraint.activateConstraints(
                        listOf(
                            composition.widthAnchor.constraintEqualToAnchor(it.widthAnchor),
                            composition.heightAnchor.constraintEqualToAnchor(it.heightAnchor)
                        )
                    )
                }
            )
        }
    }
}

