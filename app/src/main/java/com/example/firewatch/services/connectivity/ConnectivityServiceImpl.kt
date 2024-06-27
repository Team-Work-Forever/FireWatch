package com.example.firewatch.services.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import com.example.firewatch.R
import com.example.firewatch.shared.models.SyncRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ConnectivityServiceImpl(
    @ApplicationContext val context: Context,
) : ConnectivityService {
    private val connectivityManager: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)
    override val syncRepos = mutableListOf<SyncRepository>()

    override fun sync() {
        CoroutineScope(Dispatchers.IO).launch {
            val syncJobs = syncRepos.map {
                async {
                    it.sync()
                }
            }

            syncJobs.awaitAll()
        }
    }

    override fun isConnectionActive(): Boolean {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?: return false

        return networkCapabilities
            .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                &&
            networkCapabilities
                .hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            sync()
            Toast.makeText(context, context.getString(R.string.internet_up), Toast.LENGTH_LONG).show()
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            Toast.makeText(context, context.getString(R.string.internet_down), Toast.LENGTH_LONG).show()
        }

        override fun onUnavailable() {
            super.onUnavailable()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
        }
    }

    override fun listen() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            networkCallback,
        )
    }

    override fun unsubscribe() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}