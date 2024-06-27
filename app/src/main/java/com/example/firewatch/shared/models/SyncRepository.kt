package com.example.firewatch.shared.models

interface SyncRepository {
    suspend fun sync()
}