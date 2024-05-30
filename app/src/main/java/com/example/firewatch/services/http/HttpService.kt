package com.example.firewatch.services.http

import com.example.firewatch.services.http.api.AutarchyApiService
import com.example.firewatch.services.http.api.AuthApiService
import com.example.firewatch.services.http.api.BurnApiService
import com.example.firewatch.services.http.api.ProfileApiService
import com.example.firewatch.services.http.contracts.ProblemDetails
import com.example.firewatch.shared.errors.HttpFailedException
import com.example.firewatch.shared.errors.HttpProblemException
import com.google.gson.Gson
import retrofit2.Response

interface HttpService {
    val authService: AuthApiService
    val profileService: ProfileApiService
    val autarchyApiService: AutarchyApiService
    val burnApiService: BurnApiService

    companion object {
        suspend fun <TResponse> fetch(handleResponse: suspend () -> Response<TResponse>): Result<TResponse> {
            val gson = Gson()
            val response = handleResponse()

            if (response.isSuccessful) {
                val body = response.body()!!
                return Result.success(body)
            }

            val contentType = response.headers().get("Content-Type")

            if (contentType == null || contentType.contains("application/problem+json")) {
                return Result.failure(HttpFailedException())
            }

            val errorBody = response.errorBody()!!.string()
            val problem = gson.fromJson(errorBody, ProblemDetails::class.java)

            return Result.failure(HttpProblemException(problem))
        }
    }
}