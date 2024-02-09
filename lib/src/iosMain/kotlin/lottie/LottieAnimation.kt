@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "CANNOT_OVERRIDE_INVISIBLE_MEMBER")

package lottie


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.lottie_ios.CompatibleAnimationView
import androidx.compose.ui.graphics.Color
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
) {
    when (composition as? CompatibleAnimationView) {
        null -> {}
        else -> {
            UIKitView(
                factory = {
                    UIView().apply {
                        this.backgroundColor = UIColor.clearColor
                        this.opaque = false
                        this.setClipsToBounds(true)
                    }
                },
                modifier = modifier,
                update = { view ->
                    composition.translatesAutoresizingMaskIntoConstraints = false
                    view.addSubview(composition)
                    NSLayoutConstraint.activateConstraints(
                        listOf(
                            composition.widthAnchor.constraintEqualToAnchor(view.widthAnchor),
                            composition.heightAnchor.constraintEqualToAnchor(view.heightAnchor)
                        )
                    )
                },
                background = backgroundColor
            )
        }
    }



}
