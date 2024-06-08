package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kottie.lib.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes

@OptIn(ExperimentalResourceApi::class)
@Composable
fun kottieReadBytes(path: String): String {
    var json by remember { mutableStateOf("") }
    LaunchedEffect(Unit){
        json = Res.readBytes(path = path).decodeToString()
    }
    return json
}


@OptIn(InternalResourceApi::class)
@Composable
fun kottieReadResourceBytes(path: String): String {
    var json by remember { mutableStateOf("") }
    LaunchedEffect(Unit){
        json = readResourceBytes(path).decodeToString()
    }
    return json
}