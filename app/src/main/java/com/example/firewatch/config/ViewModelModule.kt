package com.example.firewatch.config

import com.example.firewatch.domain.repositories.AutarchyRepositoryImpl
import com.example.firewatch.domain.repositories.BurnRepositoryImpl
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.services.connectivity.ConnectivityService
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.persistence.DatabaseContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideBurnRepository(
        httpService: HttpService,
        connectivityService: ConnectivityService,
        dbContext: DatabaseContext
    ): BurnRepository {
        return BurnRepositoryImpl(
            httpService,
            connectivityService,
            dbContext.burnDao()
        )
    }

    @Provides
    fun provideAutarchyRepository(
        httpService: HttpService
    ): AutarchyRepository {
        return AutarchyRepositoryImpl(httpService)
    }
}
