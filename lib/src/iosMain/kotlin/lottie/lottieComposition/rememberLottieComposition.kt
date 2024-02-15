package lottie.lottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cocoapods.lottie_ios.CompatibleAnimation
import cocoapods.lottie_ios.CompatibleAnimationView
import cocoapods.lottie_ios.LottieAnimationLayer
import cocoapods.lottie_ios.LottieAnimationView
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.create
import platform.Foundation.dataTaskWithURL
import platform.Foundation.dataWithBytes
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.dataWithData
import platform.darwin.DISPATCH_QUEUE_CONCURRENT_INACTIVE
import platform.darwin.NSUInteger

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toNSData(): NSData = this.usePinned {
    NSData.create(bytes = it.addressOf(0), length = this.size.convert())
}


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
                    compatibleAnimation = CompatibleAnimation(
                        name = spec.path,
                        subdirectory = null,
                        bundle = NSBundle.mainBundle
                    )
                )
            }

            is LottieCompositionSpec.Url -> {
                val httpClient = HttpClient()
                val data = httpClient.get(spec.url)
                CompatibleAnimationView(
                    data = data.readBytes().toNSData()
                )
            }

            is LottieCompositionSpec.JsonString -> {
                CompatibleAnimationView(
                    data = spec.jsonString.encodeToByteArray().toNSData()
                )
            }

        }
        animationState = animation
    }

    return animationState

}

