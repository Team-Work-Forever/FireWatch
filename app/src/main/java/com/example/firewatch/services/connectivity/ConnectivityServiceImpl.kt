package com.example.firewatch.services.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext

class ConnectivityServiceImpl(
    @ApplicationContext val context: Context
) : ConnectivityService {
    private val connectivityManager: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)

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

            Toast.makeText(context, "Internet UP!", Toast.LENGTH_LONG).show()
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            Toast.makeText(context, "Internet Down!", Toast.LENGTH_LONG).show()
        }

        override fun onUnavailable() {
            super.onUnavailable()

            println("It was good while lasted")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            println("FEARLESS!")
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