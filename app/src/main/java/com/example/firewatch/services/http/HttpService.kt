package com.example.firewatch.services.http

import com.example.firewatch.services.http.api.AuthApiService

interface HttpService {
    val authService: AuthApiService
}