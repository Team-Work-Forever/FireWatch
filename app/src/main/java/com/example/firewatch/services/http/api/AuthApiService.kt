package com.example.firewatch.services.http.api

import com.example.firewatch.services.http.contracts.DefaultResponse
import com.example.firewatch.services.http.contracts.auth.AuthResponse
import com.example.firewatch.services.http.contracts.auth.LoginRequest
import com.example.firewatch.services.http.contracts.auth.ResetPasswordRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body() loginRequest: LoginRequest): Response<AuthResponse>

    @GET("auth/forgot_password")
    suspend fun forgotPassword(
        @Query("email") email: String
    ): Response<DefaultResponse>

    @GET("auth/reset_password")
    suspend fun resetPassword(
        @Query("forgot_token") forgotToken: String,
        @Body() resetPasswordRequest: ResetPasswordRequest
    ): Response<DefaultResponse>
    @GET("auth/refresh_tokens")
    suspend fun refreshTokens(
        @Query("token") token: String
    ): Response<AuthResponse>

    @POST("auth/signUp")
    suspend fun signUp(
       @Body() body: MultipartBody
    ) : Response<AuthResponse>
}