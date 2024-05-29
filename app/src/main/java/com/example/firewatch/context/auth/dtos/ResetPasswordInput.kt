package com.example.firewatch.context.auth.dtos

data class ResetPasswordInput(
    val forgotToken: String,
    val password: String,
    val confirmPassword: String
)