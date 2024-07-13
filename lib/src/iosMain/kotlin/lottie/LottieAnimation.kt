package lottie

import Lottie.CompatibleAnimationView
import Lottie.LottieAnimationView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import contentScale.ContentScale
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIColor
import platform.UIKit.UILayoutPriorityDefaultLow
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode


@OptIn(ExperimentalForeignApi::class)
@Composable
fun LottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color,
    contentScale: ContentScale,
    clipToCompositionBounds: Boolean
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
                    UIView().apply {
                        this.backgroundColor = UIColor.clearColor
                        this.tintColor = UIColor.clearColor
                        this.clipsToBounds = clipToCompositionBounds
                    }
                },
                background = backgroundColor,
                update = { view ->

                    composition.translatesAutoresizingMaskIntoConstraints = false
                    composition.contentMode = scaleType
                    composition.clipsToBounds = clipToCompositionBounds

                    view.addSubview(composition)

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

