package com.sanjeet.innobuzapptask.utils

sealed class NetworkResult<T> {
    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()

    /**
     *
     * To see the API state in logcat
     */
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "API Calling Success $data"
            is Failure -> "API Calling Failure $errorMessage"
            is Loading -> "API Data Loading"
        }
    }
}