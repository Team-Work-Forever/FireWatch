package com.example.firewatch.domain.repositories

import com.example.firewatch.context.auth.dtos.ProfileUpdateInput
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.domain.shared.Repository
import com.example.firewatch.services.http.HttpService

class ProfileRepositoryImpl(val httpService: HttpService) : ProfileRepository {
    override suspend fun getInfo(): Result<User> {
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

    override suspend fun update(user: ProfileUpdateInput): Result<User> {
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

    override fun create(entity: User): Result<String> {
        // TODO: Should store this on Shared Preferences
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): Result<User> {
       TODO("")
    }
}