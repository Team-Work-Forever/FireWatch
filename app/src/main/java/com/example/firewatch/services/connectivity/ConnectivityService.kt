package com.example.firewatch.services.connectivity

interface ConnectivityService {
   fun isConnectionActive(): Boolean
   fun listen()
   fun unsubscribe()
}