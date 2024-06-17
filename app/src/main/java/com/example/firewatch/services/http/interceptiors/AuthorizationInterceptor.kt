package com.example.firewatch.services.http.interceptiors

import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.types.Tokens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    private var authService: AuthService? = null

    fun setAuthService(authService: AuthService) {
        this.authService = authService
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        authService?.let {
            it.getActiveTokens()?.let { tokens ->
                request.addHeader("Authorization", "Bearer ${tokens.accessToken}")
            }
        }

        return chain.proceed(request.build())
    }
}