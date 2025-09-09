package skiaComposition

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response

actual suspend fun getAnimation(url: String): String {
    val response: Response = window.fetch(url).await()
    return response.text().await()
}