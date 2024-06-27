package com.example.firewatch.config

import android.content.Context
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
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideBurnRepository(
        @ApplicationContext context: Context,
        httpService: HttpService,
        connectivityService: ConnectivityService,
        dbContext: DatabaseContext
    ): BurnRepository {
        return BurnRepositoryImpl(
            context,
            httpService,
            connectivityService,
            dbContext
        )
    }

    @Provides
    fun provideAutarchyRepository(
        httpService: HttpService,
        connectivityService: ConnectivityService,
        dbContext: DatabaseContext
    ): AutarchyRepository {
        return AutarchyRepositoryImpl(
            httpService,
            connectivityService,
            dbContext
        )
    }
}
