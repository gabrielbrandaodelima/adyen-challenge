package com.adyen.android.assignment.core.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

open class GenericRepository {

    suspend fun <T> apiCall(apiCall: suspend () -> Response<T>): T? =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                when {
                    response.isSuccessful -> {
                        response.body()
                    }
                    response.code() == 403 -> {

                        val errorMessage = try {

                            val jObjError = response.errorBody()?.string()?.let {
                                JSONObject(it)
                            }
                            jObjError?.getString("message")

                        } catch (e: Throwable) {
                            throw Exception("An error has ocurred")
                        }
                        throw Exception(errorMessage)
                    }
                    response.code() == 503 -> {
                        throw Exception("Service Unavailable, please try again")
                    }
                    else -> {

                        val errorMessage = try {

                            val jObjError = response.errorBody()?.string()?.let {
                                JSONObject(it)
                            }
                            jObjError?.getString("message")

                        } catch (e: Throwable) {
                            throw Exception("Service Unavailable, please try again")
                        }
                        throw Exception(errorMessage)
                    }
                }

            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
}