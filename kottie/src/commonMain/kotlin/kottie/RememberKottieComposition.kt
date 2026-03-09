package kottie

import androidx.compose.runtime.Composable

/**
 * Loads and remembers a Lottie animation composition.
 *
 * This function loads the animation from the specified [spec] and caches
 * the result. The loading happens asynchronously and the returned
 * [KottieComposition] will transition through states:
 * 1. [KottieComposition.Loading] - Initially while fetching/parsing
 * 2. [KottieComposition.Success] or [KottieComposition.Failure] - Final state
 *
 * Example usage:
 * ```kotlin
 * // Load from URL
 * val composition = rememberKottieComposition(
 *     KottieCompositionSpec.Url("https://lottie.host/xxx/animation.json")
 * )
 *
 * // Load from file
 * var json by remember { mutableStateOf("") }
 * LaunchedEffect(Unit) {
 *     json = Res.readBytes("files/animation.json").decodeToString()
 * }
 * val composition = rememberKottieComposition(
 *     KottieCompositionSpec.File(json)
 * )
 *
 * // Handle states
 * when (composition) {
 *     is KottieComposition.Loading -> { /* Show loading */ }
 *     is KottieComposition.Success -> { /* Show animation */ }
 *     is KottieComposition.Failure -> { /* Show error */ }
 * }
 * ```
 *
 * @param spec The specification for loading the animation (URL, file content, or JSON string)
 * @return The current loading state of the composition
 */
@Composable
expect fun rememberKottieComposition(spec: KottieCompositionSpec): KottieComposition
