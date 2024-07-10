import android.widget.ImageView.ScaleType
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import contentScale.ContentScale

@Composable
actual fun KottieAnimation(
    modifier: Modifier,
    composition: Any?,
    progress: () -> Float,
    backgroundColor: Color,
    contentScale: ContentScale
) {

    val scaleType = when(contentScale){
        ContentScale.Fit -> androidx.compose.ui.layout.ContentScale.Fit
        ContentScale.Crop -> androidx.compose.ui.layout.ContentScale.Crop
        ContentScale.FillBounds -> androidx.compose.ui.layout.ContentScale.FillBounds
    }

    LottieAnimation(
        composition = composition as? LottieComposition,
        progress = { progress() },
        modifier = modifier
            .background(backgroundColor),
        contentScale = scaleType
    )

}