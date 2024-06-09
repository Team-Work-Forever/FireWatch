package com.example.firewatch.services.http.interceptiors

import com.example.firewatch.context.auth.AuthServiceImpl
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer ${AuthServiceImpl.tokens?.first}")

        return chain.proceed(request.build())
    }
}