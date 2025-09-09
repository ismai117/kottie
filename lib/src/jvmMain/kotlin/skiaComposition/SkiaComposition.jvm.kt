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
    if (response.statusCode() !in 200..299) {
        throw Exception("Failed to fetch animation from $url: ${response.statusCode()}")
    }
    val responseBody = response.body()
    if (responseBody.isBlank()) {
        throw Exception("Received empty animation data from $url")
    }
    return responseBody
}