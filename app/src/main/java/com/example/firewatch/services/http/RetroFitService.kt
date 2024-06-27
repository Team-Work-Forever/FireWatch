package com.example.firewatch.services.http

import com.example.firewatch.services.http.api.AutarchyApiService
import com.example.firewatch.services.http.api.AuthApiService
import com.example.firewatch.services.http.api.BurnApiService
import com.example.firewatch.services.http.api.ProfileApiService
import com.example.firewatch.services.http.contracts.profile.ProfileResultResponse
import com.example.firewatch.services.http.interceptiors.AcceptLanguageInterceptor
import com.example.firewatch.services.http.interceptiors.AuthorizationInterceptor
import com.example.firewatch.services.http.serializeres.ProfileSerializer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitService(
    authenticationInterceptor: AuthorizationInterceptor,
    acceptLanguageInterceptor: AcceptLanguageInterceptor
) : HttpService {
    private val client: OkHttpClient.Builder = OkHttpClient.Builder()

    companion object {
        private const val BASE_URL = "https://firewatchrest.onrender.com/api/v1/"
    }

    private val retrofit: Retrofit by lazy {
        client.addInterceptor(authenticationInterceptor)
        client.addInterceptor(acceptLanguageInterceptor)

        val gsonOptions = GsonBuilder()
            .registerTypeAdapter(ProfileResultResponse::class.java, ProfileSerializer())
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonOptions))
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