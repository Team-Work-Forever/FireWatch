package com.example.firewatch.config

import android.content.Context
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.AuthServiceImpl
import com.example.firewatch.domain.repositories.ProfileRepositoryImpl
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.RetroFitService
import com.example.firewatch.services.http.interceptiors.AuthorizationInterceptor
import com.example.firewatch.services.persistence.DatabaseContext
import com.example.firewatch.services.persistence.FireWatchDatabase
import com.example.firewatch.services.store.StoreController
import com.example.firewatch.services.store.StoreControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyModule {
    @Provides
    @Singleton
    fun provideDatabaseContext(
        @ApplicationContext context: Context
    ): DatabaseContext {
        return FireWatchDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor {
        return AuthorizationInterceptor()
    }
