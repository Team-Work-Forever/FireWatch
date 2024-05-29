package com.example.firewatch.context.dtos

data class ResetPasswordInput(
    val forgotToken: String,
    val password: String,
    val confirmPassword: String
)