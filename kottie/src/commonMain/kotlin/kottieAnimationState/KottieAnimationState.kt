package kottieAnimationState

data class KottieAnimationState(
    val composition: Any? = null,
    val progress: Float = 0f,
    val iterations: Int = 0,
    val reverseOnRepeat: Boolean = false,
    val isPlaying: Boolean = false,
    val isCompleted: Boolean = false,
    val duration: Float = 0f,
    val speed: Float = 0f
)
