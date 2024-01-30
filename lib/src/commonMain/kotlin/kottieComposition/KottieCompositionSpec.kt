package kottieComposition

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource
import kotlin.jvm.JvmInline


sealed interface KottieCompositionSpec {
    @JvmInline
    value class Url(val url: String) : KottieCompositionSpec
    @OptIn(ExperimentalResourceApi::class)
    @JvmInline
    value class File (val fileName: Resource) : KottieCompositionSpec
    @JvmInline
    value class JsonString(val jsonString: String) : KottieCompositionSpec

}