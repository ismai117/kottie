package kottieComposition


data class KottieCompositionResult(
    val value: Any? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isComplete: Boolean = false,
    val isFailed: Boolean = false
) {

    val isSuccess: Boolean
        get() = isComplete && error == null && value != null

    companion object {
        fun loading(): KottieCompositionResult = KottieCompositionResult(
            isLoading = true
        )
        
        fun success(value: Any): KottieCompositionResult = KottieCompositionResult(
            value = value,
            isComplete = true
        )
        
        fun error(error: Throwable): KottieCompositionResult = KottieCompositionResult(
            error = error,
            isComplete = true,
            isFailed = true
        )
    }
}