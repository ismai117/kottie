package kottieComposition

import kotlin.jvm.JvmInline


sealed interface KottieCompositionSpec {
    @JvmInline
    value class Url(val url: String) : KottieCompositionSpec
    @JvmInline
    value class File(val jsonString: String) : KottieCompositionSpec
    @JvmInline
    value class JsonString(val jsonString: String) : KottieCompositionSpec
}