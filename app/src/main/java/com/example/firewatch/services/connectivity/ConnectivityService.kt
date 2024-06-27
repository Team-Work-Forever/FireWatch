package com.example.firewatch.services.connectivity

import com.example.firewatch.shared.models.SyncRepository

interface ConnectivityService {
   val syncRepos: MutableList<SyncRepository>

   fun sync()
   fun isConnectionActive(): Boolean
   fun listen()
   fun unsubscribe()
}