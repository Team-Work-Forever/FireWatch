package com.example.firewatch.services.http.contracts.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token")  val refreshToken: String
)