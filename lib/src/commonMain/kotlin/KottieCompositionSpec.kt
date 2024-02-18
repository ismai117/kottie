import kottie.lib.generated.resources.Res
import kotlin.jvm.JvmInline


sealed interface KottieCompositionSpec {
    @JvmInline
    value class Url(val url: String) : KottieCompositionSpec
    @JvmInline
    value class File (val path: String) : KottieCompositionSpec
    @JvmInline
    value class JsonString(val jsonString: String) : KottieCompositionSpec

}