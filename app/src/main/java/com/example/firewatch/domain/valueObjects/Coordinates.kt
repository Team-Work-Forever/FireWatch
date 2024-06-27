package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo
import com.example.firewatch.R
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.utils.TranslateUtil
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
        fun new(lat: BigDecimal, lon: BigDecimal): Coordinates {
            return Coordinates(lat, lon)
        }

        fun create(
            lat: BigDecimal,
            lon: BigDecimal,
        ): Result<Coordinates> {
            if (lat.compareTo(BigDecimal.ZERO) == 0 || lon.compareTo(BigDecimal.ZERO) == 0) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_a_valid_coordinate)))
            }

            return Result.success(
                Coordinates(
                    lat,
                    lon
                )
            )
        }
    }
}