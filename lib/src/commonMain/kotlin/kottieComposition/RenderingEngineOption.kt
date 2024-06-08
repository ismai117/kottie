package kottieComposition

import kotlin.jvm.JvmInline


expect sealed class RenderMode {
    val AUTOMATIC: Any?
    val MAIN: Any?
}