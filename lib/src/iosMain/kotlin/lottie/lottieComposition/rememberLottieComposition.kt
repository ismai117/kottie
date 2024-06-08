package lottie.lottieComposition

import Lottie.CompatibleAnimationView
import Lottie.CompatibleRenderingEngineOptionAutomatic
import Lottie.CompatibleRenderingEngineOptionCoreAnimation
import Lottie.CompatibleRenderingEngineOptionDefaultEngine
import Lottie.CompatibleRenderingEngineOptionMainThread
import Lottie.CompatibleRenderingEngineOptionShared
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIColor


@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toNSData(): NSData = memScoped {
    NSData.create(bytes = allocArrayOf(this@toNSData),
        length = this@toNSData.size.toULong())
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
                    data = spec.jsonString.encodeToByteArray().toNSData(),
                    compatibleRenderingEngineOption = CompatibleRenderingEngineOptionAutomatic
                )
            }

            is LottieCompositionSpec.Url -> {
                val httpClient = HttpClient()
                val data = httpClient.get(spec.url)
                CompatibleAnimationView(
                    data = data.readBytes().toNSData(),
                    compatibleRenderingEngineOption = CompatibleRenderingEngineOptionAutomatic
                )
            }

            is LottieCompositionSpec.JsonString -> {
                CompatibleAnimationView(
                    data = spec.jsonString.encodeToByteArray().toNSData(),
                    compatibleRenderingEngineOption = CompatibleRenderingEngineOptionAutomatic
                )
            }

        }
        animationState = animation
    }

    return animationState

}

