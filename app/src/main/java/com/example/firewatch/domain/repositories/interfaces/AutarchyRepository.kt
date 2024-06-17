package com.example.firewatch.domain.repositories.interfaces

import com.example.firewatch.domain.repositories.dtos.autarchy.AutarchyCreateInput
import com.example.firewatch.domain.repositories.dtos.autarchy.AutarchyUpdateInput
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.shared.models.Repository
import com.example.firewatch.services.http.contracts.Pagination
import java.time.LocalDate

interface AutarchyRepository : Repository<Autarchy> {
    suspend fun createAutarchy(input: AutarchyCreateInput): Result<String>
    suspend fun update(input: AutarchyUpdateInput): Result<Autarchy>
    suspend fun getAll(
        search: String? = null,
        pagination: Pagination? = null
    ): Result<List<Autarchy>>
    suspend fun getAllBurns(
        id: String,
        search: String? = null,
        state: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        pagination: Pagination? = null
    ): Result<List<Burn>>
}