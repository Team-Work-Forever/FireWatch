package com.example.firewatch.config

import android.content.Context
import com.example.firewatch.context.AuthService
import com.example.firewatch.context.AuthServiceImpl
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.RetroFitService

interface AppModule {
    val httpService: HttpService
    val authService: AuthService
    val appContext: Context
}

class AppModuleImpl(
    override val appContext: Context
) : AppModule {
    override val httpService: HttpService by lazy {
        RetroFitService
    }

    override val authService: AuthService by lazy {
        AuthServiceImpl(httpService.authService)
    }
}