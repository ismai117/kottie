package skiaComposition

import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse

actual suspend fun getAnimation(url: String): String {
    val client = java.net.http.HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build()
    val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
    return response.body()
}