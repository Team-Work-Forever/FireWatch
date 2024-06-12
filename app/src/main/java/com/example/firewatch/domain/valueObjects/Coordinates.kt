package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo
import java.math.BigDecimal

class Coordinates(
    @ColumnInfo("lat") val lat: BigDecimal,
    @ColumnInfo("lon") val lon: BigDecimal,
) {
    fun getLatDefinition(): String {
        val scaledBigDecimal = lat.setScale(6, BigDecimal.ROUND_HALF_UP)
        return scaledBigDecimal.toPlainString()
    }

    fun getLonDefinition(): String {
        val scaledBigDecimal = lon.setScale(6, BigDecimal.ROUND_HALF_UP)
        return scaledBigDecimal.toPlainString()
    }

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