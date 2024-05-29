package com.example.firewatch.context

import com.example.firewatch.context.dtos.ResetPasswordInput
import com.example.firewatch.context.dtos.SignUpInput
import com.example.firewatch.data.entities.User
import com.example.firewatch.services.http.contracts.auth.ResetPasswordRequest

interface AuthService {
    suspend fun forgotPassword(email: String): Result<String>
    suspend fun resetPassword(input: ResetPasswordInput): Result<String>
    suspend fun login(email: String, password: String): Result<String>
    suspend fun signUp(input: SignUpInput): Result<String>
    fun getIdentity(): User
    suspend fun checkAuth(token: String): Result<String>
}