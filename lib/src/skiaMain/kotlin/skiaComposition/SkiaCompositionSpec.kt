package skiaComposition

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource
import kotlin.jvm.JvmInline


internal sealed interface SkiaCompositionSpec {
    @JvmInline
    value class Url(val url: String) : SkiaCompositionSpec
    @OptIn(ExperimentalResourceApi::class)
    @JvmInline
    value class File (val fileName: Resource) : SkiaCompositionSpec
    @JvmInline
    value class JsonString(val jsonString: String) : SkiaCompositionSpec

}