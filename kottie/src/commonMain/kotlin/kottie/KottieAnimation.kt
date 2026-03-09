package kottie

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kottie.internal.KottieAnimationInternal

/**
 * Displays a Lottie animation.
 *
 * This composable renders a Lottie animation at the specified progress.
 * Use [rememberKottieComposition] to load the animation and
 * [animateKottieCompositionAsState] to animate it.
 *
 * Example usage:
 * ```kotlin
 * val composition = rememberKottieComposition(
 *     KottieCompositionSpec.Url("https://example.com/animation.json")
 * )
 *
 * val animationState by animateKottieCompositionAsState(
 *     composition = composition,
 *     iterations = Kottie.IterateForever
 * )
 *
 * when (composition) {
 *     is KottieComposition.Loading -> CircularProgressIndicator()
 *     is KottieComposition.Failure -> Text("Error loading animation")
 *     is KottieComposition.Success -> {
 *         KottieAnimation(
 *             composition = composition,
 *             progress = { animationState.progress },
 *             modifier = Modifier.size(200.dp)
 *         )
 *     }
 * }
 * ```
 *
 * @param composition The loaded animation composition from [rememberKottieComposition].
 *   Must be a [KottieComposition.Success] for the animation to render.
 * @param progress Lambda that returns the current animation progress from 0f to 1f.
 *   Using a lambda allows for efficient recomposition.
 * @param modifier Modifier for the animation container
 * @param backgroundColor Background color behind the animation (default: transparent)
 * @param contentScale How to scale the animation within its bounds (default: [ContentScale.Fit])
 * @param clipToCompositionBounds Whether to clip content to the composition bounds (default: true)
 */
@Composable
fun KottieAnimation(
    composition: KottieComposition,
    progress: () -> Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    contentScale: ContentScale = ContentScale.Fit,
    clipToCompositionBounds: Boolean = true,
) {
    val internalComposition = (composition as? KottieComposition.Success)?.composition

    KottieAnimationInternal(
        modifier = modifier,
        composition = internalComposition,
        progress = progress,
        backgroundColor = backgroundColor,
        contentScale = contentScale,
        clipToCompositionBounds = clipToCompositionBounds
    )
}
