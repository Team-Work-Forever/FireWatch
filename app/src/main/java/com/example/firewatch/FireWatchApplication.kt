package com.example.firewatch

import android.app.Application
import com.example.firewatch.services.connectivity.ConnectivityService
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

    override fun onCreate() {
        super.onCreate()

        TranslateUtil.context = this
        connectivityService.listen()
    }
}