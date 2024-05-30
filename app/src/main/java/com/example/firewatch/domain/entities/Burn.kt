package com.example.firewatch.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.example.firewatch.shared.models.BaseEntity
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import java.time.LocalDateTime

@Entity(tableName = "burns")
class Burn(
    id: String,
    @ColumnInfo("title") val title: String,
    @Embedded val coordinates: Coordinates,
    @ColumnInfo("has_aid_team") val hasAidTeam: Boolean,
    @ColumnInfo("reason") val reason: BurnReason,
    @ColumnInfo("type") val type: BurnType,
    @ColumnInfo("begin_at") val beginAt: LocalDateTime,
    @ColumnInfo("completed_at") val completedAt: LocalDateTime?,
    @ColumnInfo("map_picture") val mapPicture: String,
    @ColumnInfo("state") val state: BurnState,
) : BaseEntity(id) {
    companion object {
        fun create(
            id: String,
            title: String,
            coordinates: Coordinates,
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
                coordinates,
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