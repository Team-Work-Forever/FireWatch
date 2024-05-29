package com.example.firewatch.domain.repositories.interfaces

import com.example.firewatch.context.auth.dtos.ProfileUpdateInput
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.shared.Repository

interface ProfileRepository : Repository<User> {
    suspend fun getInfo(): Result<User>
    suspend fun update(user: ProfileUpdateInput): Result<User>
}