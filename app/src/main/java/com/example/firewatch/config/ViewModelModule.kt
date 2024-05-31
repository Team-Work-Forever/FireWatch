package com.example.firewatch.config

import com.example.firewatch.domain.repositories.AutarchyRepositoryImpl
import com.example.firewatch.domain.repositories.BurnRepositoryImpl
import com.example.firewatch.domain.repositories.ProfileRepositoryImpl
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.http.HttpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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
    fun provideProfileRepository(
        httpService: HttpService
    ): ProfileRepository {
        return ProfileRepositoryImpl(httpService)
    }
}