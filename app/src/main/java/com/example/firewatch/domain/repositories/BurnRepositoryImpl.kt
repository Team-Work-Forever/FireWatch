package com.example.firewatch.domain.repositories

import com.example.firewatch.domain.daos.BurnDao
import com.example.firewatch.domain.repositories.dtos.burn.BurnCreateInput
import com.example.firewatch.domain.repositories.dtos.burn.BurnUpdateInput
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.dtos.burn.BurnRequest
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.services.connectivity.ConnectivityService
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.contracts.Pagination
import com.example.firewatch.services.persistence.DatabaseContext
import com.example.firewatch.shared.utils.DateUtils
import java.math.BigDecimal
import java.time.LocalDateTime

class BurnRepositoryImpl(
    private val httpService: HttpService,
    private val connectivityService: ConnectivityService,
    private val dbContext: DatabaseContext
) : BurnRepository {
    override suspend fun create(input: BurnCreateInput): Result<BurnRequest> {
        try {
            val response = HttpService.fetch {
                httpService.burnApiService.create(input.toMultipart())
            }

            val body = response.getOrThrow()
            val result = Result.success(BurnRequest(body.burnId, BurnState.get(body.state)!!))

            if (connectivityService.isConnectionActive()) {
                val entity = input.toBurn(body.burnId, BurnState.get(body.state)!!)
                dbContext.burnDao().create(entity)
            }

            return result
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun update(input: BurnUpdateInput): Result<Burn> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.update(input.id, input.toMultipart())
        }

        val result = response.getOrThrow()
        val burn = result.properties.toBurn(result.geometry.getCoordinate())

        Result.success(burn)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAvailabitity(coordinates: Coordinates): Result<Boolean> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.getAvailability(
                coordinates.lat,
                coordinates.lon,
                true
            )
        }

        val result = response.getOrThrow()
        Result.success(result.result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun terminate(id: String): Result<BurnState> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.terminate(
                id
            )
        }

        val result = response.getOrThrow()
        val burnState = BurnState.get(result.state) ?: throw Exception("wasn't possible to convert to burn state")

        Result.success(burnState)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun start(id: String): Result<BurnState> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.start(
                id
            )
        }

        val result = response.getOrThrow()
        val burnState = BurnState.get(result.state) ?: throw Exception("wasn't possible to convert to burn state")

        Result.success(burnState)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getTypes(): Result<List<BurnType>> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.getTypes()
        }

        val result = response.getOrThrow().map {
            BurnType.get(it)!!
        }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getReasons(): Result<List<BurnReason>> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.getReasons()
        }

        val result = response.getOrThrow().map {
            BurnReason.get(it)!!
        }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getStates(): Result<List<BurnState>> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.getStates()
        }

        val result = response.getOrThrow().map {
            BurnState.get(it)!!
        }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAll(
        search: String?,
        state: String?,
        sort: String?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        pagination: Pagination?
    ): Result<List<Burn>> {
        try {
            if (!connectivityService.isConnectionActive()) {
                val result = dbContext.burnDao().getAll(
//                    search = search,
//                    state = state,
//                    startDate = startDate,
//                    endDate = endDate,
//                    limit = 10,
//                    offset = 1
                )

                return Result.success(result)
            }

            val response = HttpService.fetch {
                httpService.burnApiService.getAll(
                    search = search,
                    state = state,
                    sort = sort,
                    startDate = startDate?.let { DateUtils.toString(startDate) },
                    endDate = endDate?.let { DateUtils.toString(endDate) },
                    page = pagination?.page ?: Pagination.PAGE,
                    pageSize = pagination?.pageSize ?: Pagination.PAGE_SIZE
                )
            }

            val result = response.getOrThrow().features.map {
                it.properties.toBurn(it.geometry.getCoordinate())
            }

            pagination?.totalPages = result.size
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun get(id: String): Result<Burn> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.getById(id)
        }

        val result = response.getOrThrow()
        val burn = result.properties.toBurn(result.geometry.getCoordinate())

        Result.success(burn)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun delete(id: String): Result<String> = try {
        val response = HttpService.fetch {
            httpService.burnApiService.delete(id)
        }

        val result = response.getOrThrow().burnId
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}