package com.example.firewatch.shared.broadCastRecivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkChangeListener : BroadcastReceiver() {
    private fun getConnectivityManager(context: Context?): ConnectivityManager {
        return context?.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager = getConnectivityManager(context)
        val activeNetWork = connectivityManager.activeNetwork ?: return
        val network = connectivityManager.getNetworkCapabilities(activeNetWork) ?: return
    }
}