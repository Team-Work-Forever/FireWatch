package com.example.firewatch.context.auth.types

import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.api.AuthApiService
import com.example.firewatch.services.http.contracts.auth.LoginRequest
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class EmailAuthentication(
    val email: String,
    val password: String
) : Authentication() {
    override suspend fun getTokens(api: AuthApiService): Result<Tokens> = try
    {
        val response = HttpService.fetch {
            api.login(LoginRequest(email, password))
        }

        val tokens = response.getOrThrow()
        success(Tokens(tokens.accessToken, tokens.refreshToken))
    } catch (e: Exception) {
        failure(e)
    }
}
