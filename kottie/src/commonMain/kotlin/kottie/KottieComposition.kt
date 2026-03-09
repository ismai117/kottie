package kottie

/**
 * Represents the result of loading a Lottie animation composition.
 *
 * This is a sealed interface with three possible states:
 * - [Loading]: The composition is currently being loaded
 * - [Success]: The composition was loaded successfully
 * - [Failure]: The composition failed to load
 *
 * Example usage:
 * ```kotlin
 * val composition = rememberKottieComposition(KottieCompositionSpec.Url("..."))
 *
 * when (composition) {
 *     is KottieComposition.Loading -> CircularProgressIndicator()
 *     is KottieComposition.Success -> KottieAnimation(composition = composition, ...)
 *     is KottieComposition.Failure -> Text("Error: ${composition.error.message}")
 * }
 * ```
 */
sealed interface KottieComposition {

    /**
     * The composition is currently being loaded.
     */
    data object Loading : KottieComposition

    /**
     * The composition was loaded successfully.
     *
     * The underlying platform-specific composition is held internally
     * and used by [KottieAnimation] for rendering.
     */
    @ConsistentCopyVisibility
    data class Success internal constructor(
        internal val composition: Any
    ) : KottieComposition

    /**
     * The composition failed to load.
     *
     * @property error The exception that caused the failure
     */
    data class Failure(val error: Throwable) : KottieComposition
}

/**
 * Returns true if this composition is a [KottieComposition.Success].
 */
val KottieComposition.isSuccess: Boolean
    get() = this is KottieComposition.Success

/**
 * Returns true if this composition is a [KottieComposition.Loading].
 */
val KottieComposition.isLoading: Boolean
    get() = this is KottieComposition.Loading

/**
 * Returns true if this composition is a [KottieComposition.Failure].
 */
val KottieComposition.isFailure: Boolean
    get() = this is KottieComposition.Failure

/**
 * Returns the error if this is a [KottieComposition.Failure], or null otherwise.
 */
val KottieComposition.errorOrNull: Throwable?
    get() = (this as? KottieComposition.Failure)?.error
