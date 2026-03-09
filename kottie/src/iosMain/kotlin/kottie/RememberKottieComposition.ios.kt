package kottie

import Lottie.CompatibleAnimationView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import io.ktor.http.isSuccess
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import platform.Foundation.NSData
import platform.Foundation.create

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun ByteArray.toNSData(): NSData = memScoped {
    NSData.create(
        bytes = allocArrayOf(this@toNSData),
        length = this@toNSData.size.toULong()
    )
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberKottieComposition(spec: KottieCompositionSpec): KottieComposition {
    var state by remember(spec) { mutableStateOf<KottieComposition>(KottieComposition.Loading) }

    LaunchedEffect(spec) {
        state = when (spec) {
            is KottieCompositionSpec.File -> {
                if (spec.jsonString.isBlank()) {
                    KottieComposition.Failure(IllegalArgumentException("JSON string is empty"))
                } else {
                    try {
                        val animation = CompatibleAnimationView(
                            data = spec.jsonString.encodeToByteArray().toNSData()
                        )
                        KottieComposition.Success(animation)
                    } catch (e: Exception) {
                        KottieComposition.Failure(e)
                    }
                }
            }

            is KottieCompositionSpec.Url -> {
                try {
                    val httpClient = HttpClient()
                    val response = httpClient.get(spec.url)
                    httpClient.close()

                    if (response.status.isSuccess()) {
                        val animation = CompatibleAnimationView(
                            data = response.readRawBytes().toNSData()
                        )
                        KottieComposition.Success(animation)
                    } else {
                        KottieComposition.Failure(
                            Exception("HTTP request failed with status: ${response.status}")
                        )
                    }
                } catch (e: Exception) {
                    KottieComposition.Failure(e)
                }
            }

            is KottieCompositionSpec.JsonString -> {
                if (spec.jsonString.isBlank()) {
                    KottieComposition.Failure(IllegalArgumentException("JSON string is empty"))
                } else {
                    try {
                        val animation = CompatibleAnimationView(
                            data = spec.jsonString.encodeToByteArray().toNSData()
                        )
                        KottieComposition.Success(animation)
                    } catch (e: Exception) {
                        KottieComposition.Failure(e)
                    }
                }
            }
        }
    }

    return state
}
