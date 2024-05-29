package com.example.firewatch.services.http.api

import com.example.firewatch.services.http.contracts.profile.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApiService {
    @GET("whoami")
    suspend fun info() : Response<ProfileResponse>

    @PUT("profile")
    suspend fun updateProfile(
        @Body() body: MultipartBody
    ) : Response<ProfileResponse>
}