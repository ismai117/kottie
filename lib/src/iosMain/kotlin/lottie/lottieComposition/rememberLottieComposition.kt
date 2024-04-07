package lottie.lottieComposition

import Lottie.CompatibleAnimationView
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
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes
import platform.Foundation.NSData
import platform.Foundation.create

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toNSData(): NSData = this.usePinned {
    NSData.create(bytes = it.addressOf(0), length = this.size.convert())
}


@OptIn(ExperimentalForeignApi::class, InternalResourceApi::class)
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
                    data = readResourceBytes(spec.path).toNSData()
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

