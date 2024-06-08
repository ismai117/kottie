package kottieComposition

import Lottie.CompatibleRenderingEngineOptionAutomatic
import Lottie.CompatibleRenderingEngineOptionMainThread
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual sealed class RenderMode {
    actual val AUTOMATIC: Any?
        get() = CompatibleRenderingEngineOptionAutomatic
    actual val MAIN: Any?
        get() = CompatibleRenderingEngineOptionMainThread
}