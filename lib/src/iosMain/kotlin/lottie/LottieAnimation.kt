package lottie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.lottie_ios.CompatibleAnimation
import cocoapods.lottie_ios.CompatibleAnimationView
import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.skia.skottie.Animation
import platform.CoreGraphics.CGFloat
import platform.Foundation.NSBundle
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIColor
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
fun LottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float
) {
    when (composition as? CompatibleAnimationView) {
        null -> {}
        else -> {
            UIKitView(
                factory = {
                    UIView().apply {
                        backgroundColor = UIColor.clearColor()
                        opaque = true
                        setClipsToBounds(true)
                    }
                },
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
                modifier = modifier.fillMaxSize(),
                background = MaterialTheme.colorScheme.surface
            )
        }
    }
}

