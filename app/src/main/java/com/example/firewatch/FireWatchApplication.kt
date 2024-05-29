package com.example.firewatch

import android.app.Application
import com.example.firewatch.config.AppModule
import com.example.firewatch.config.AppModuleImpl

class FireWatchApplication : Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}