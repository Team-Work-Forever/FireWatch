package com.example.firewatch.config

import android.content.Context
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.AuthServiceImpl
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.RetroFitService
import com.example.firewatch.services.http.interceptiors.AuthorizationInterceptor
import com.example.firewatch.services.persistence.DatabaseContext
import com.example.firewatch.services.persistence.FireWatchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DependencyModule {
    @Provides
    fun provideDatabaseContext(
        @ApplicationContext context: Context
    ): DatabaseContext {
        return FireWatchDatabase.getDatabase(context)
    }

    @Provides
    fun provideHttpService(): HttpService {
        return RetroFitService()
    }
}