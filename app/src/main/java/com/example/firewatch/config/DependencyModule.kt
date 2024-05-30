package com.example.firewatch.config

import com.example.firewatch.domain.repositories.BurnRepositoryImpl
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.RetroFitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DependencyModule {

    @Provides
    fun provideHttpService(
    ): HttpService {
        return RetroFitService
    }

    @Provides
    fun provideBurnRepository(
        httpService: HttpService
    ): BurnRepository {
        return BurnRepositoryImpl(httpService)
    }

}