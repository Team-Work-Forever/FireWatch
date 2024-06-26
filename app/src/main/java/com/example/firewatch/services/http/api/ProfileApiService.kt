package com.example.firewatch.services.http.api

import com.example.firewatch.services.http.contracts.profile.ProfileResultResponse
import com.example.firewatch.services.http.contracts.profile.PublicProfileResponse
import com.example.firewatch.services.http.contracts.profile.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProfileApiService {
    @GET("whoami")
    suspend fun info() : Response<ProfileResultResponse>

    @GET("public/profile")
    suspend fun getPublicProfile(
        @Query("email") email: String
    ): Response<PublicProfileResponse>

    @PUT("profile")
    suspend fun updateProfile(
        @Body() body: MultipartBody
    ) : Response<UserProfileResponse>
}