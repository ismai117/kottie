package kottie.internal

import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse

internal actual suspend fun fetchAnimationJson(url: String): String {
    val client = java.net.http.HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build()

    val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()

    if (response.statusCode() !in 200..299) {
        throw Exception("HTTP request failed with status ${response.statusCode()}: $url")
    }

    val body = response.body()
    if (body.isBlank()) {
        throw Exception("Received empty response from $url")
    }

    return body
}
