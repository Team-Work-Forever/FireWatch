package com.example.firewatch.services.http.api

import com.example.firewatch.services.http.contracts.auth.AuthResponse
import com.example.firewatch.services.http.contracts.auth.LoginRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    fun login(@Body() loginRequest: LoginRequest): Call<AuthResponse>

    @POST("auth/signUp")
    fun signUp(
       @Body() body: MultipartBody
    ) : Call<AuthResponse>
}