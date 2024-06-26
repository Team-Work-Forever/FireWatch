package com.example.firewatch.domain.repositories

import com.example.firewatch.domain.repositories.dtos.autarchy.AutarchyCreateInput
import com.example.firewatch.domain.repositories.dtos.autarchy.AutarchyUpdateInput
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.services.connectivity.ConnectivityService
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.contracts.Pagination
import com.example.firewatch.services.persistence.DatabaseContext
import java.time.LocalDateTime

class AutarchyRepositoryImpl(
    private val httpService: HttpService,
    private val connectivityService: ConnectivityService,
    private val dbContext: DatabaseContext
) : AutarchyRepository {
    override suspend fun createAutarchy(input: AutarchyCreateInput): Result<String> {
        try {
            val response = HttpService.fetch {
                httpService.autarchyApiService.create(input.toMultipart())
            }

            val body = response.getOrThrow()
            val result = Result.success(body.autarchyId)

            if (connectivityService.isConnectionActive()) {
                val entity = input.toAutarchy(body.autarchyId)
                dbContext.autarcharyDao().create(entity)
            }

            return result
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun update(input: AutarchyUpdateInput): Result<Autarchy> {
        return try {
            val response = HttpService.fetch {
                httpService.autarchyApiService.update(
                    input.id,
                    input.toMultipart()
                )
            }

            val result = response.getOrThrow()
            val autarchy = result.properties.toAutarchy(result.geometry.getCoordinate())

            Result.success(autarchy)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun get(id: String): Result<Autarchy> = try {
        val response = HttpService.fetch {
            httpService.autarchyApiService.getById(id)
        }

        val result = response.getOrThrow()
        val autarchy = result.properties.toAutarchy(result.geometry.getCoordinate())

        Result.success(autarchy)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun delete(id: String): Result<String> = try {
        val response = HttpService.fetch {
            httpService.autarchyApiService.delete(id)
        }

        val result = response.getOrThrow().autarchyId
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAll(search: String?, pagination: Pagination?): Result<List<Autarchy>> = try {
        val response = HttpService.fetch {
            httpService.autarchyApiService.getAll(
                search = search,
                page = pagination?.page ?: Pagination.PAGE,
                pageSize = pagination?.pageSize ?: Pagination.PAGE_SIZE
            )
        }

        val result = response.getOrThrow().features.map {
            it.properties.toAutarchy(it.geometry.getCoordinate())
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
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        pagination: Pagination?
    ): Result<List<Burn>> {
        return try {
            val response = HttpService.fetch {
                httpService.autarchyApiService.getAllBurns(
                    id,
                    search = search,
                    state = state,
                    startDate = startDate,
                    endDate = endDate,
                    page = pagination?.page ?: Pagination.PAGE,
                    pageSize = pagination?.pageSize ?: Pagination.PAGE_SIZE
                )
            }

            val result = response.getOrThrow().features.map {
                it.properties.toBurn(it.geometry.getCoordinate())
            }

            pagination?.totalPages = result.size
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}