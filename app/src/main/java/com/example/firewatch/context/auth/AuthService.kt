package com.example.firewatch.context.auth

import com.example.firewatch.context.auth.dtos.ResetPasswordInput
import com.example.firewatch.context.auth.dtos.SignUpInput
import com.example.firewatch.context.auth.types.Authentication
import com.example.firewatch.context.auth.types.Tokens
import com.example.firewatch.domain.entities.IdentityUser
import kotlinx.coroutines.Deferred

interface AuthService {
    suspend fun authentication(authentication: Authentication): Result<Boolean>
    suspend fun refreshTokens(): Result<Boolean>
    suspend fun forgotPassword(email: String): Result<String>
    suspend fun resetPassword(input: ResetPasswordInput): Result<String>
    suspend fun signUp(input: SignUpInput): Result<String>
    fun logout(): Boolean
    fun <TUserType : IdentityUser> getIdentity(): Result<TUserType>
    fun getDefaultIdentify(): Result<IdentityUser>
    fun getActiveTokens(): Tokens?
    suspend fun fetchProfile(): Result<Boolean>
    suspend fun checkAuth(): Result<Boolean>
}