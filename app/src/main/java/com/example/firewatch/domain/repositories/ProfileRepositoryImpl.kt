package com.example.firewatch.domain.repositories

import com.example.firewatch.domain.repositories.dtos.profile.ProfileUpdateInput
import com.example.firewatch.domain.entities.IdentityUser
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.connectivity.ConnectivityService
import com.example.firewatch.services.http.HttpService

class ProfileRepositoryImpl(
    private val httpService: HttpService,
    private val connectivityService: ConnectivityService
) : ProfileRepository {
    override suspend fun getInfo(): Result<IdentityUser> {
        return try {
              val response = HttpService.fetch {
                httpService.profileService.info()
            }

            val result = response.getOrThrow().toIdentityUser()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(user: ProfileUpdateInput): Result<IdentityUser> {
        return try {
            val response = HttpService.fetch {
                httpService.profileService.updateProfile(user.toMultipart())
            }

            val result = response.getOrThrow().toIdentityUser()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun get(id: String): Result<IdentityUser> {
        TODO("")
    }

    override suspend fun delete(id: String): Result<String> {
        TODO("Not yet implemented")
    }
}