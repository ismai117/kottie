package kottie

/**
 * Represents the current playback state of a Kottie animation.
 *
 * This state is returned by [animateKottieCompositionAsState] and provides
 * information about the animation's progress and playback status.
 *
 * Example usage:
 * ```kotlin
 * val animationState by animateKottieCompositionAsState(
 *     composition = composition,
 *     iterations = Kottie.IterateForever
 * )
 *
 * KottieAnimation(
 *     composition = composition,
 *     progress = { animationState.progress }
 * )
 *
 * // Check completion
 * if (animationState.isCompleted) {
 *     // Animation finished all iterations
 * }
 * ```
 *
 * @property progress Current animation progress from 0f (start) to 1f (end)
 * @property isPlaying Whether the animation is currently playing
 * @property isCompleted Whether the animation has finished all iterations
 * @property iteration Current iteration number (starts at 0)
 * @property duration Total duration of one animation cycle in seconds
 * @property speed Current playback speed multiplier (1f = normal speed)
 */
data class KottieAnimationState(
    val progress: Float = 0f,
    val isPlaying: Boolean = false,
    val isCompleted: Boolean = false,
    val iteration: Int = 0,
    val duration: Float = 0f,
    val speed: Float = 1f
)
