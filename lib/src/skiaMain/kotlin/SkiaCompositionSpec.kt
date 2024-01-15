import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource
import kotlin.jvm.JvmInline


internal sealed interface SkiaCompositionSpec {
    @JvmInline
    value class Url(val url: String) : SkiaCompositionSpec
    @JvmInline
    value class File @OptIn(ExperimentalResourceApi::class) constructor(val fileName: Resource) : SkiaCompositionSpec
    @JvmInline
    value class JsonString(val jsonString: String) : SkiaCompositionSpec

}