package com.example.firewatch.services.http

import com.example.firewatch.services.http.api.AutarchyApiService
import com.example.firewatch.services.http.api.AuthApiService
import com.example.firewatch.services.http.api.ProfileApiService

interface HttpService {
    val authService: AuthApiService
    val profileService: ProfileApiService
    val autarchyApiService: AutarchyApiService
}