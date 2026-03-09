package kottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

/**
 * Animates a Kottie composition and returns the animation state.
 *
 * This function manages the animation playback and provides the current
 * progress through [KottieAnimationState]. Use the returned state's
 * [KottieAnimationState.progress] with [KottieAnimation].
 *
 * Example usage:
 * ```kotlin
 * val composition = rememberKottieComposition(
 *     KottieCompositionSpec.Url("https://example.com/animation.json")
 * )
 *
 * // Basic usage - plays once
 * val animationState by animateKottieCompositionAsState(composition)
 *
 * // Loop forever
 * val animationState by animateKottieCompositionAsState(
 *     composition = composition,
 *     iterations = Kottie.IterateForever
 * )
 *
 * // With playback control
 * var isPlaying by remember { mutableStateOf(true) }
 * val animationState by animateKottieCompositionAsState(
 *     composition = composition,
 *     isPlaying = isPlaying,
 *     iterations = Kottie.IterateForever
 * )
 *
 * // Use with KottieAnimation
 * KottieAnimation(
 *     composition = composition,
 *     progress = { animationState.progress }
 * )
 * ```
 *
 * @param composition The loaded composition from [rememberKottieComposition]
 * @param isPlaying Whether the animation should be playing (default: true)
 * @param restartOnPlay Whether to restart from the beginning when [isPlaying] becomes true (default: true)
 * @param reverseOnRepeat Whether to play backwards on alternate iterations (default: false)
 * @param speed Playback speed multiplier. 1f = normal, 2f = 2x speed, 0.5f = half speed (default: 1f)
 * @param iterations Number of times to play. Use [Kottie.IterateForever] to loop forever (default: 1)
 * @param useCompositionFrameRate Whether to use the animation's native frame rate instead of display refresh rate (default: false)
 * @return State containing the current [KottieAnimationState]
 */
@Composable
expect fun animateKottieCompositionAsState(
    composition: KottieComposition,
    isPlaying: Boolean = true,
    restartOnPlay: Boolean = true,
    reverseOnRepeat: Boolean = false,
    speed: Float = 1f,
    iterations: Int = 1,
    useCompositionFrameRate: Boolean = false
): State<KottieAnimationState>
