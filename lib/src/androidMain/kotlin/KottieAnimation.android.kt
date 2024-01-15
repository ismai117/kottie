import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation



@SuppressLint("DiscouragedApi")
@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float
) {

    LottieAnimation(
        composition = composition as? LottieComposition,
        progress = { progress() },
        modifier = modifier
    )

}

