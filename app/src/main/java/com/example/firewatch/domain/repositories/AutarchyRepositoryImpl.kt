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
        val response = HttpService.fetch {
            httpService.autarchyApiService.create(input.toMultipart())
        }

        Result.success(response.getOrThrow().autarchyId)
    } catch (e: Exception) {
        Result.failure(e)
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
        startDate: LocalDate?,
        endDate: LocalDate?,
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

    override fun create(entity: Autarchy): Result<String> {
        TODO("Not yet implemented")
    }
}