package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo
import java.math.BigDecimal

class Coordinates private constructor(
    @ColumnInfo("lat") val lat: BigDecimal,
    @ColumnInfo("lon") val lon: BigDecimal,
) {
    companion object {
        fun create(
            lat: BigDecimal,
            lon: BigDecimal,
        ): Coordinates {
            return Coordinates(
                lat,
                lon
            )
        }
    }
}