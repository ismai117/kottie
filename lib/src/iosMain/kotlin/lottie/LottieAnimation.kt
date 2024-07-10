@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "CANNOT_OVERRIDE_INVISIBLE_MEMBER")

package lottie

import Lottie.AnimatedButton.Companion.setAnimationRepeatAutoreverses
import Lottie.CompatibleAnimationView
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.constrain
import androidx.compose.ui.unit.dp
import contentScale.ContentScale
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRectZero
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
    backgroundColor: Color,
    contentScale: ContentScale
) {
    when (composition as? CompatibleAnimationView) {
        null -> {}
        else -> {

            val scaleType = when(contentScale){
                ContentScale.Fit -> UIViewContentMode.UIViewContentModeScaleAspectFit
                ContentScale.Crop -> UIViewContentMode.UIViewContentModeScaleAspectFill
                ContentScale.FillBounds -> UIViewContentMode.UIViewContentModeScaleToFill
            }

            UIKitView(
                modifier = modifier,
                factory = {
                    val view = UIView()
                    view.backgroundColor = UIColor.clearColor
                    view.tintColor = UIColor.clearColor
                    view.clipsToBounds = true
                    view
                },
                background = backgroundColor,
                update = { view ->
                    view.addSubview(composition)
                    composition.contentMode = scaleType
                    composition.translatesAutoresizingMaskIntoConstraints = false
                    NSLayoutConstraint.activateConstraints(
                        listOf(
                            composition.widthAnchor.constraintEqualToAnchor(view.widthAnchor),
                            composition.heightAnchor.constraintEqualToAnchor(view.heightAnchor)
                        )
                    )
                }
            )

        }
    }
}
