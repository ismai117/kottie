package kottieComposition

import androidx.compose.runtime.Composable
import kottieComposition.KottieCompositionSpec


@Composable
expect fun rememberKottieComposition(spec: KottieCompositionSpec): Any?