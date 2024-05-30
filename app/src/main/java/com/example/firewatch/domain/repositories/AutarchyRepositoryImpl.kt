package com.example.firewatch.domain.repositories

import com.example.firewatch.context.auth.dtos.AutarchyCreateInput
import com.example.firewatch.context.auth.dtos.AutarchyUpdateInput
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.contracts.Pagination
import java.time.LocalDate

class AutarchyRepositoryImpl(private val httpService: HttpService) : AutarchyRepository {
    override suspend fun createAutarchy(input: AutarchyCreateInput): Result<String> = try {
        val response = httpService.autarchyApiService.create(input.toMultipart())

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!.toString()
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun update(input: AutarchyUpdateInput): Result<Autarchy> {
        return try {
            val response = httpService.autarchyApiService.update(
                input.id,
                input.toMultipart()
            )

            if (!response.isSuccessful) {
                val error = response.errorBody()!!.string()
                Result.failure<Exception>(Exception(error))
            }

            val result = response.body()!!.properties.toAutarchy()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun create(entity: Autarchy): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): Result<Autarchy> {
        return try {
            val response = httpService.autarchyApiService.getById(id)

            if (!response.isSuccessful) {
                val error = response.errorBody()!!.string()
                Result.failure<Exception>(Exception(error))
            }

            val result = response.body()!!.properties.toAutarchy()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: String): Result<String> {
        return try {
            val response = httpService.autarchyApiService.delete(id)

            if (!response.isSuccessful) {
                val error = response.errorBody()!!.string()
                Result.failure<Exception>(Exception(error))
            }

            val result = response.body()!!.autarchyId
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAll(search: String?, pagination: Pagination?): Result<List<Autarchy>> = try {
        val response = httpService.autarchyApiService.getAll(
            search = search,
            page = pagination?.page ?: Pagination.PAGE,
            pageSize = pagination?.pageSize ?: Pagination.PAGE_SIZE
        )

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!.features.map {
            it.properties.toAutarchy()
        }

        pagination?.totalPages = result.size
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllBurns(
        id: String,
        search: String?,
        state: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        pagination: Pagination?
    ): Result<List<Burn>> {
        return try {
            val response = httpService.autarchyApiService.getAllBurns(
                id,
                search = search,
                state = state,
                startDate = startDate,
                endDate = endDate,
                page = pagination?.page ?: Pagination.PAGE,
                pageSize = pagination?.pageSize ?: Pagination.PAGE_SIZE
            )

            if (!response.isSuccessful) {
                val error = response.errorBody()!!.string()
                Result.failure<Exception>(Exception(error))
            }

            val result = response.body()!!.features.map {
                it.properties.toBurn()
            }

            pagination?.totalPages = result.size
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}