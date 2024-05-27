package com.example.firewatch.services.http

import com.example.firewatch.services.http.api.AuthApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface HttpService {
    val authService: AuthApiService
}

object RetroFitService: HttpService {
    private const val BASE_URL = "http://10.0.2.2:3000/api/v1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override val authService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}