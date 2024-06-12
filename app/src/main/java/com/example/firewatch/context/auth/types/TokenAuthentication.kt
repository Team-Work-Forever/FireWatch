package com.example.firewatch.context.auth.types

import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.api.AuthApiService
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class TokenAuthentication(
    val refreshToken: String
) : Authentication() {
    override suspend fun getTokens(api: AuthApiService): Result<Tokens> = try {
        val response = HttpService.fetch {
            api.refreshTokens(refreshToken)
        }

        val tokens = response.getOrThrow()
        success(Tokens(tokens.accessToken, tokens.refreshToken))
    } catch (e: Exception) {
        failure(e)
    }
}
