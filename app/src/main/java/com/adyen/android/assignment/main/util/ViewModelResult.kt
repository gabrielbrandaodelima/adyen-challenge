package com.adyen.android.assignment.main.util


sealed class ViewModelResult<out T>() {
    internal data class Success<T>(val value: T? = null) : ViewModelResult<T>()

    internal data class SuccessOnlyUpdate<T>(private val value: T? = null) : ViewModelResult<T>() {
        var hasBeenHandled = false
            private set

        fun getContentIfNotHandled(): T? {
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                value
            }
        }

        fun peekContent(): T? = value
    }

    internal data class Failure<T>(val cause: Throwable?) : ViewModelResult<T>()

    internal data class Loading<T>(val message: String? = null, val progress: Float? = null) : ViewModelResult<T>()

    internal data class LoadingValue<T, E>(val value: E? = null) : ViewModelResult<T>()
}


