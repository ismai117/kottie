package skiaComposition

import kotlinx.browser.window
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual suspend fun getAnimation(url: String): String {
    return suspendCancellableCoroutine { continuation ->
        window.fetch(url)
            .then {
                if (it.ok) {
                    it.text().then {
                        continuation.resume(it.toString())
                        null
                    }
                } else{
                    continuation.invokeOnCancellation{}
                }
                null
            }
            .catch { e ->
                continuation.invokeOnCancellation{}
                null
            }
    }
}
