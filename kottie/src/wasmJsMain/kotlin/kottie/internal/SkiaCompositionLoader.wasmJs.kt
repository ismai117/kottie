package kottie.internal

import kotlinx.browser.window
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(kotlin.js.ExperimentalJsExport::class)
internal actual suspend fun fetchAnimationJson(url: String): String {
    return suspendCancellableCoroutine { continuation ->
        window.fetch(url)
            .then { response ->
                if (response.ok) {
                    response.text().then { text ->
                        continuation.resume(text.toString())
                        null
                    }.catch { e ->
                        continuation.resumeWithException(Exception("Failed to read response text: $e"))
                        null
                    }
                } else {
                    continuation.resumeWithException(
                        Exception("HTTP request failed with status ${response.status}: $url")
                    )
                }
                null
            }
            .catch { e ->
                continuation.resumeWithException(Exception("Network error fetching $url: $e"))
                null
            }
    }
}
