package lottie


import Lottie.CompatibleAnimationView
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
data class LottieAnimationState(
    val composition: CompatibleAnimationView? = null,
    val isPlaying: Boolean = false,
    val isCompleted: Boolean = false,
    val progress: Float = 0f,
    val iterations: Int = 0,
    val speed: Float = 0f,
    val duration: Float = 0f
)
