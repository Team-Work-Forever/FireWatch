package com.example.firewatch.config

import android.content.Context
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.AuthServiceImpl
import com.example.firewatch.domain.repositories.ProfileRepositoryImpl
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.RetroFitService

interface AppModule {
    val httpService: HttpService
    val authService: AuthService
    val autarchyRepository: AutarchyRepository
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

    override val autarchyRepository: AutarchyRepository by lazy {
        AutarchyRepositoryImpl(httpService)
    }}