package com.example.firewatch.config

import android.content.Context
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.AuthServiceImpl
import com.example.firewatch.domain.repositories.AutarchyRepositoryImpl
import com.example.firewatch.domain.repositories.BurnRepositoryImpl
import com.example.firewatch.domain.repositories.ProfileRepositoryImpl
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.RetroFitService
import com.example.firewatch.services.http.interceptiors.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideBurnRepository(
        httpService: HttpService
    ): BurnRepository {
        return BurnRepositoryImpl(httpService)
    }

    @Provides
    fun provideAutarchyRepository(
        httpService: HttpService
    ): AutarchyRepository {
        return AutarchyRepositoryImpl(httpService)
    }
    @Provides
    fun provideAuthService(
        httpService: HttpService,
        authorizationInterceptor: AuthorizationInterceptor,
        profileRepository: ProfileRepository
    ): AuthService {
        return AuthServiceImpl(
            httpService,
            authorizationInterceptor,
            profileRepository,
        )
    }
    @Provides
    fun provideProfileRepository(
        httpService: HttpService
    ): ProfileRepository {
        return ProfileRepositoryImpl(httpService)
    }
}