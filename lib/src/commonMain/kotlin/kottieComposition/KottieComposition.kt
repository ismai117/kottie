package kottieComposition

import KottieCompositionSpec
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
expect fun kottieComposition (
    spec: KottieCompositionSpec
): Any?