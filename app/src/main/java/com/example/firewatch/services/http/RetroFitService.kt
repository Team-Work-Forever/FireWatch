package com.example.firewatch.services.http

import com.example.firewatch.services.http.api.AutarchyApiService
import com.example.firewatch.services.http.api.AuthApiService
import com.example.firewatch.services.http.api.BurnApiService
import com.example.firewatch.services.http.api.ProfileApiService
import com.example.firewatch.services.http.interceptiors.AuthorizationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitService : HttpService {
    private val client: OkHttpClient.Builder = OkHttpClient.Builder()
    private const val BASE_URL = "http://10.0.2.2:3000/api/v1/"

    private val retrofit: Retrofit by lazy {
        client.addInterceptor(AuthorizationInterceptor())

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    override val authService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    override val profileService: ProfileApiService by lazy {
        retrofit.create(ProfileApiService::class.java)
    }
    override val autarchyApiService: AutarchyApiService by lazy {
        retrofit.create(AutarchyApiService::class.java)
    }
    override val burnApiService: BurnApiService by lazy {
        retrofit.create(BurnApiService::class.java)
    }
}