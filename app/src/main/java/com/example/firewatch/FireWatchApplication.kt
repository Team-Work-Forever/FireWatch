package com.example.firewatch

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.firewatch.config.ViewModelModule
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.services.connectivity.ConnectivityService
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.persistence.DatabaseContext
import com.example.firewatch.services.store.StoreController
import com.example.firewatch.shared.utils.TranslateUtil
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FireWatchApplication : Application() {

    @Inject
    lateinit var storeController: StoreController

    @Inject
    lateinit var connectivityService: ConnectivityService

    @Inject
    lateinit var httpService: HttpService

    @Inject
    lateinit var dbContext: DatabaseContext

    override fun onCreate() {
        super.onCreate()

        TranslateUtil.context = this
        connectivityService.listen()

        connectivityService.syncRepos.add(ViewModelModule.provideBurnRepository(
            this,
            httpService,
            connectivityService,
            dbContext
        ))
    }
}