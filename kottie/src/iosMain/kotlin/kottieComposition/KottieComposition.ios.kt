package kottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import lottie.lottieComposition.LottieCompositionSpec

@Composable
actual fun kottieComposition(
    spec: KottieCompositionSpec
): Any? {
    var lottieSpec by remember {  mutableStateOf<LottieCompositionSpec?>( null) }
    LaunchedEffect(spec){
        lottieSpec = when(spec){
            is KottieCompositionSpec.File -> {
                LottieCompositionSpec.File(spec.jsonString)
            }
            is KottieCompositionSpec.Url -> {
                LottieCompositionSpec.Url(spec.url)
            }
            is KottieCompositionSpec.JsonString -> {
                LottieCompositionSpec.JsonString(spec.jsonString)
            }
        }
    }
    return    lottieSpec
}