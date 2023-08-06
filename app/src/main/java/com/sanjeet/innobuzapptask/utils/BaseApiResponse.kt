package com.sanjeet.innobuzapptask.utils

import android.util.Log
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    Log.e("BaseApiResponse ","API success response code is : ${response.code()} \nAPI response is : ${response.body()}")
                    return NetworkResult.Success(body)
                }
            }
            Log.e("BaseApiResponse ","API failure response code is : ${response.code()}  \nAPI failure message is: ${response.message()}")
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Log.e("BaseApiResponse ","Api calling Exception is: ${e.message}")
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Failure("Api call failed $errorMessage")
}