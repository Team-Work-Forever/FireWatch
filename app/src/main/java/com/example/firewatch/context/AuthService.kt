package com.example.firewatch.context

import com.example.firewatch.context.dtos.SignUpInput
import com.example.firewatch.data.entities.User

interface AuthService {
    suspend fun login(email: String, password: String): Boolean
    suspend fun signUp(input: SignUpInput): Boolean
    fun getIdentity(): User
    suspend fun checkAuth(): Boolean
}