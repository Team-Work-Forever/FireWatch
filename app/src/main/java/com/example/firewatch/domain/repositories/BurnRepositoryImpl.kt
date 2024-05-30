package com.example.firewatch.domain.repositories

import com.example.firewatch.context.auth.dtos.BurnCreateInput
import com.example.firewatch.context.auth.dtos.BurnUpdateInput
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.dtos.BurnRequest
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.contracts.Pagination
import com.example.firewatch.shared.utils.DateUtils
import java.time.LocalDateTime

class BurnRepositoryImpl(private val httpService: HttpService) : BurnRepository {
    override suspend fun create(input: BurnCreateInput): Result<BurnRequest> = try {
        val response = httpService.burnApiService.create(input.toMultipart())

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!
        Result.success(BurnRequest(result.burnId, BurnState.get(result.state)!!))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun create(entity: Burn): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun update(input: BurnUpdateInput): Result<Burn> = try {
        val response = httpService.burnApiService.update(input.id, input.toMultipart())

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!.properties.toBurn()
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAvailabitity(coordinates: Coordinates): Boolean = try {
        val response = httpService.burnApiService.getAvailability(
            coordinates.lat,
            coordinates.lon
        )

        val result = response.body()!!
        result.result
    } catch (e: Exception) {
        false
    }

    override suspend fun getTypes(): Result<List<BurnType>> = try {
        val response = httpService.burnApiService.getTypes()

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!.map {
            BurnType.get(it)!!
        }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getReasons(): Result<List<BurnReason>> = try {
        val response = httpService.burnApiService.getReasons()

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!.map {
            BurnReason.get(it)!!
        }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getStates(): Result<List<BurnState>> = try {
        val response = httpService.burnApiService.getStates()

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!.map {
            BurnState.get(it)!!
        }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAll(
        search: String?,
        state: String?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        pagination: Pagination?
    ): Result<List<Burn>> = try {
        val response = httpService.burnApiService.getAll(
            search = search,
            state = state,
            startDate = startDate?.let { DateUtils.toString(startDate) },
            endDate = endDate?.let { DateUtils.toString(endDate) },
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

    override suspend fun get(id: String): Result<Burn> = try {
        val response = httpService.burnApiService.getById(id)

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!.properties.toBurn()
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun delete(id: String): Result<String> = try {
        val response = httpService.burnApiService.delete(id)

        if (!response.isSuccessful) {
            val error = response.errorBody()!!.string()
            Result.failure<Exception>(Exception(error))
        }

        val result = response.body()!!.burnId
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

}