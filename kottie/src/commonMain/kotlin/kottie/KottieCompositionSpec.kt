package kottie

import kotlin.jvm.JvmInline

/**
 * Specification for loading a Lottie animation composition.
 *
 * Use one of the following to create a spec:
 * - [Url] - Load from a remote URL
 * - [File] - Load from a JSON string (typically read from a file)
 * - [JsonString] - Load from a raw JSON string
 *
 * Example usage:
 * ```kotlin
 * // From URL
 * val spec = KottieCompositionSpec.Url("https://example.com/animation.json")
 *
 * // From file content
 * val json = Res.readBytes("files/animation.json").decodeToString()
 * val spec = KottieCompositionSpec.File(json)
 *
 * // From inline JSON
 * val spec = KottieCompositionSpec.JsonString("""{"v": "5.7.4", ...}""")
 * ```
 */
sealed interface KottieCompositionSpec {

    /**
     * Load animation from a remote URL.
     *
     * @property url The URL to fetch the Lottie JSON from
     */
    @JvmInline
    value class Url(val url: String) : KottieCompositionSpec

    /**
     * Load animation from a JSON string read from a file.
     *
     * This is semantically the same as [JsonString] but indicates
     * the source was a file for clarity.
     *
     * @property jsonString The Lottie animation JSON content
     */
    @JvmInline
    value class File(val jsonString: String) : KottieCompositionSpec

    /**
     * Load animation from a raw JSON string.
     *
     * @property jsonString The Lottie animation JSON content
     */
    @JvmInline
    value class JsonString(val jsonString: String) : KottieCompositionSpec
}
