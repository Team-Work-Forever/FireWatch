package com.example.firewatch.services.http.interceptiors

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmaXJld2F0Y2guY29tIiwic3ViIjoiMjU3ZTI2ZmMtMjU5Yi00MzZiLTk5OTAtZDJmN2QyYzg0M2U1IiwiYXVkIjpbIkZpcmUgV2F0Y2giXSwiZXhwIjoxNzE3MjUxMDE4LCJpYXQiOjE3MTcwNzgyMTgsImp0aSI6IjAzMTExMTZmLWYyMGEtNDgyOS05MWQ0LTRlNTc5NjQwNTYwZiIsImVtYWlsIjoiZGlvZ29hc3N1bmNhb0BpcHZjLnB0Iiwicm9sZSI6InVzZXIifQ.ivY0jGZNm0Lsq9HXKtE6qM5hEigZBp4z60yU1XFa3Ro")

        return chain.proceed(request.build())
    }
}