package com.example.firewatch.domain.repositories.interfaces

import com.example.firewatch.domain.repositories.dtos.profile.ProfileUpdateInput
import com.example.firewatch.domain.entities.IdentityUser
import com.example.firewatch.domain.entities.PublicProfile
import com.example.firewatch.domain.valueObjects.Email
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.models.Repository

interface ProfileRepository : Repository<IdentityUser> {
    suspend fun getInfo(): Result<IdentityUser>
    suspend fun getPublicProfile(email: String): Result<PublicProfile>
    suspend fun update(user: ProfileUpdateInput): Result<IdentityUser>
}