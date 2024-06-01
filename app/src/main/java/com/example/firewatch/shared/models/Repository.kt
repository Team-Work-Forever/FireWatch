package com.example.firewatch.shared.models

interface Repository<TEntity : BaseEntity> {
    suspend fun get(id: String): Result<TEntity>
    suspend fun delete(id: String): Result<String>
}