package kottieComposition

import com.airbnb.lottie.RenderMode

actual sealed class RenderMode {
    actual val AUTOMATIC: Any?
        get() = RenderMode.AUTOMATIC
    actual val MAIN: Any?
        get() = RenderMode.HARDWARE

}