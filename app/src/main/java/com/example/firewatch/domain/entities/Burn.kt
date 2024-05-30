package com.example.firewatch.domain.entities

import com.example.firewatch.shared.models.Entity
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import java.time.LocalDateTime

class Burn private constructor(
    id: String,
    val title: String,
    val hasAidTeam: Boolean,
    val reason: BurnReason,
    val type: BurnType,
    val beginAt: LocalDateTime,
    val completedAt: LocalDateTime?,
    val mapPicture: String,
    val state: BurnState,
) : Entity(id) {
    companion object {
        fun create(
            id: String,
            title: String,
            hasAidTeam: Boolean,
            reason: BurnReason,
            type: BurnType,
            beginAt: LocalDateTime,
            completedAt: LocalDateTime?,
            mapPicture: String,
            state: BurnState,
        ): Burn {
            return Burn(
                id,
                title,
                hasAidTeam,
                reason,
                type,
                beginAt,
                completedAt,
                mapPicture,
                state,
            )
        }
    }
}