package kottie

/**
 * Constants and utilities for Kottie animations.
 */
object Kottie {
    /**
     * Use this value for the `iterations` parameter to loop the animation forever.
     *
     * Example:
     * ```kotlin
     * val animationState by animateKottieCompositionAsState(
     *     composition = composition,
     *     iterations = Kottie.IterateForever
     * )
     * ```
     */
    const val IterateForever: Int = Int.MAX_VALUE
}
