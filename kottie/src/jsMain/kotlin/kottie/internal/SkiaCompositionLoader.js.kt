package kottie.internal

import kotlinx.browser.window
import kotlinx.coroutines.await

internal actual suspend fun fetchAnimationJson(url: String): String {
    val response = window.fetch(url).await()
    if (!response.ok) {
        throw Exception("HTTP request failed with status ${response.status}: $url")
    }
    return response.text().await()
}
