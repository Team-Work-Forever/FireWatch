package com.example.firewatch.services.http.interceptiors

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmaXJld2F0Y2guY29tIiwic3ViIjoiMjU3ZTI2ZmMtMjU5Yi00MzZiLTk5OTAtZDJmN2QyYzg0M2U1IiwiYXVkIjpbIkZpcmUgV2F0Y2giXSwiZXhwIjoxNzE3ODg3NTgwLCJpYXQiOjE3MTc3MTQ3ODAsImp0aSI6IjljYTJkNGMyLWViNDEtNDU1OS05ZjQ2LWU3OGMwNTQyMjk0ZSIsImVtYWlsIjoiZGlvZ29hc3N1bmNhb0BpcHZjLnB0Iiwicm9sZSI6ImFkbWluIn0.bNc1M08mkrqnLHyeU6_QRf2iHbuM0MvsAvtD-YY2Ih0")

        return chain.proceed(request.build())
    }
}