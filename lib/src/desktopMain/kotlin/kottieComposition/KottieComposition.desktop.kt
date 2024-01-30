package kottieComposition

import SkiaCompositionSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun kottieComposition(
    spec: KottieCompositionSpec
): Any? {
    var skiaSpec by remember {  mutableStateOf<SkiaCompositionSpec?>( null) }
    LaunchedEffect(spec){
        skiaSpec = when(spec){
            is KottieCompositionSpec.File -> {
                SkiaCompositionSpec.File(spec.fileName)
            }
            is KottieCompositionSpec.JsonString -> {
                SkiaCompositionSpec.JsonString(spec.jsonString)
            }
            is KottieCompositionSpec.Url -> {
                SkiaCompositionSpec.Url(spec.url)
            }
        }
    }
    return skiaSpec
}