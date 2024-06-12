package com.example.firewatch.domain.repositories.interfaces

import com.example.firewatch.domain.repositories.dtos.burn.BurnCreateInput
import com.example.firewatch.domain.repositories.dtos.burn.BurnUpdateInput
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.dtos.burn.BurnRequest
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.services.http.contracts.Pagination
import com.example.firewatch.shared.models.Repository
import java.time.LocalDateTime

interface BurnRepository : Repository<Burn> {
    suspend fun create(input: BurnCreateInput): Result<BurnRequest>
    suspend fun update(input: BurnUpdateInput): Result<Burn>
    suspend fun getAvailabitity(coordinates: Coordinates): Result<Boolean>
    suspend fun getTypes(): Result<List<BurnType>>
    suspend fun getReasons(): Result<List<BurnReason>>
    suspend fun getStates(): Result<List<BurnState>>
    suspend fun getAll(
        search: String? = null,
        state: String? = null,
        sort: String? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
        pagination: Pagination? = null
    ): Result<List<Burn>>
}