package lottie.lottieComposition

import Lottie.CompatibleAnimationView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.isSuccess
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kottieComposition.KottieCompositionResult
import platform.Foundation.NSData
import platform.Foundation.create


@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toNSData(): NSData = memScoped {
    NSData.create(bytes = allocArrayOf(this@toNSData),
        length = this@toNSData.size.toULong())
}


@OptIn(ExperimentalForeignApi::class)
@Composable
internal fun rememberLottieComposition(
    spec: LottieCompositionSpec
): KottieCompositionResult {

    var animationState by remember(spec) {
        mutableStateOf(KottieCompositionResult.loading())
    }

    LaunchedEffect(spec) {
        val animation = when (spec) {
            is LottieCompositionSpec.File -> {
                KottieCompositionResult.success(CompatibleAnimationView(
                    data = spec.jsonString.encodeToByteArray().toNSData()
                ))
            }

            is LottieCompositionSpec.Url -> {
                try {
                    val httpClient = HttpClient()
                    val data = httpClient.get(spec.url)
                    if (data.status.isSuccess()) {
                        val animation = CompatibleAnimationView(
                            data = data.readBytes().toNSData()
                        )
                        KottieCompositionResult.success(animation)
                    } else {
                        KottieCompositionResult.error(Exception("Network failed with status code - ${data.status}"))
                    }
                } catch (e: Exception) {
                    KottieCompositionResult.error(e)
                }
            }

            is LottieCompositionSpec.JsonString -> {
                val animation = CompatibleAnimationView(
                    data = spec.jsonString.encodeToByteArray().toNSData()
                )
                KottieCompositionResult.success(animation)
            }

        }
        animationState = animation
    }

    return animationState

}

