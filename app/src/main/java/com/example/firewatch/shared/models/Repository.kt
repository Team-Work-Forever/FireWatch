package com.example.firewatch.shared.models

interface Repository<TEntity : Entity> {
    fun create(entity: TEntity): Result<String>
    suspend fun get(id: String): Result<TEntity>
    suspend fun delete(id: String): Result<String>
}