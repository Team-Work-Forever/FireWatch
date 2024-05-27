package com.example.firewatch.services.http.contracts.auth

data class LoginRequest
(
    val email: String,
    val password: String
)