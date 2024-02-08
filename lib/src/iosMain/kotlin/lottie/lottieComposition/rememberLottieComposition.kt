package lottie.lottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cocoapods.lottie_ios.CompatibleAnimation
import cocoapods.lottie_ios.CompatibleAnimationView
import cocoapods.lottie_ios.LottieAnimationView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.dataTaskWithURL
import platform.Foundation.dataWithContentsOfURL


@OptIn(ExperimentalForeignApi::class)
@Composable
internal fun rememberLottieComposition(
    spec: LottieCompositionSpec
): CompatibleAnimationView? {

    var animationState by remember(spec) {
        mutableStateOf<CompatibleAnimationView?>(null)
    }

    LaunchedEffect(spec) {
        val animation = when (spec) {
            is LottieCompositionSpec.File -> {
                CompatibleAnimationView(
                    CompatibleAnimation(
                        name = spec.path,
                        subdirectory = null,
                        bundle = NSBundle.mainBundle
                    )
                )
            }

            is LottieCompositionSpec.Url -> {
                val data = NSData.dataWithContentsOfURL(NSURL.URLWithString(spec.url)!!)
                if (data != null){
                    CompatibleAnimationView(
                        data = data
                    )
                }else{
                    null
                }
            }

//            is LottieCompositionSpec.JsonString -> {
//                try {
//                    CompatibleAnimationView(
//                        CompatibleAnimation(
//                            json = spec.jsonString
//                        )
//                    )
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    null
//                }
//            }

        }
        animationState = animation
    }

    return animationState

}

