package com.example.firewatch.domain.shared

interface Repository<TEntity : Entity> {
    fun create(entity: TEntity): Result<String>
    suspend fun get(id: String): Result<TEntity>
}