package com.example.firewatch.context.auth

import com.example.firewatch.context.auth.dtos.ResetPasswordInput
import com.example.firewatch.context.auth.dtos.SignUpInput
import com.example.firewatch.domain.entities.IdentityUser

interface AuthService {
    suspend fun forgotPassword(email: String): Result<String>
    suspend fun resetPassword(input: ResetPasswordInput): Result<String>
    suspend fun login(email: String, password: String): Result<String>
    suspend fun signUp(input: SignUpInput): Result<String>

    fun <TUserType : IdentityUser> getIdentity(): Result<TUserType>
    suspend fun checkAuth(token: String): Result<String>
}