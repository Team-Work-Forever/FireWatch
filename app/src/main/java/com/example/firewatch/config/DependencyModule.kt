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
import dagger.hilt.components.SingletonComponent
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

    @Provides
    @Singleton
    fun provideHttpService(
       authorizationInterceptor: AuthorizationInterceptor
    ): HttpService {
        return RetroFitService(authorizationInterceptor)
    }

    @Provides
    fun provideAuthService(
        httpService: HttpService,
        authorizationInterceptor: AuthorizationInterceptor,
        profileRepository: ProfileRepository,
        storeController: StoreController
    ): AuthService {
        return AuthServiceImpl(
            httpService,
            authorizationInterceptor,
            profileRepository,
            storeController
        )
    }
    @Provides
    fun provideProfileRepository(
        httpService: HttpService
    ): ProfileRepository {
        return ProfileRepositoryImpl(httpService)
    }
    @Provides
    fun provideStoreController(@ApplicationContext context: Context): StoreController = StoreControllerImpl(context)
}