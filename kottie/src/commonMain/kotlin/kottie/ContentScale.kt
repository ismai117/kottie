package kottie

/**
 * Represents how the Lottie animation should be scaled within its bounds.
 *
 * This is similar to [androidx.compose.ui.layout.ContentScale] but specific
 * to Lottie animations and mapped to platform-specific scaling modes.
 */
enum class ContentScale {
    /**
     * Scale the animation uniformly to fit within the bounds while
     * maintaining aspect ratio. The entire animation will be visible,
     * but there may be empty space on the sides or top/bottom.
     */
    Fit,

    /**
     * Scale the animation uniformly to fill the bounds while maintaining
     * aspect ratio. The animation may be cropped on the sides or top/bottom.
     */
    Crop,

    /**
     * Scale the animation non-uniformly to exactly fill the bounds.
     * The animation's aspect ratio will not be preserved.
     */
    FillBounds
}
