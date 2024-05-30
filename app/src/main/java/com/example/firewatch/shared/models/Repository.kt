package com.example.firewatch.shared.models

interface Repository<TEntity : BaseEntity> {
    fun create(entity: TEntity): Result<String>
    suspend fun get(id: String): Result<TEntity>
    suspend fun delete(id: String): Result<String>
}