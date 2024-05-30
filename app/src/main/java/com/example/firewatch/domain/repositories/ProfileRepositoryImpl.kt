package com.example.firewatch.domain.repositories

import com.example.firewatch.context.auth.dtos.ProfileUpdateInput
import com.example.firewatch.domain.entities.IdentityUser
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.http.HttpService

class ProfileRepositoryImpl(val httpService: HttpService) : ProfileRepository {
    override suspend fun getInfo(): Result<IdentityUser> {
         return try {
            val response = httpService.profileService.info()

            if (!response.isSuccessful) {
                val error = response.errorBody()!!.string()
                Result.failure<Exception>(Exception(error))
            }

            val result = response.body()!!.toUser()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(user: ProfileUpdateInput): Result<IdentityUser> {
         return try {
            val response = httpService.profileService.updateProfile(user.toMultipart())

            if (!response.isSuccessful) {
                val error = response.errorBody()!!.string()
                Result.failure<Exception>(Exception(error))
            }

            val result = response.body()!!.toUser()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun create(entity: IdentityUser): Result<String> {
        // TODO: Should store this on Shared Preferences
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): Result<IdentityUser> {
       TODO("")
    }

    override suspend fun delete(id: String): Result<String> {
        TODO("Not yet implemented")
    }

    suspend fun getAll(): Result<List<IdentityUser>> {
        TODO("Not yet implemented")
    }
}