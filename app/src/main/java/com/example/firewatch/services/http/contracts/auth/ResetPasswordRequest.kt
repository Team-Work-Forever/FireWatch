package com.example.firewatch.services.http.contracts.auth

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("password") val password: String,
    @SerializedName("confirm_password") val confirmPassword: String
)