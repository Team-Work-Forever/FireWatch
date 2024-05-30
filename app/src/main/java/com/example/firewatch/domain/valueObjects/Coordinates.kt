package com.example.firewatch.domain.valueObjects

import java.math.BigDecimal

class Coordinates private constructor(
    val lat: BigDecimal,
    val lon: BigDecimal,
) {
    companion object {
        fun create(
            lat: BigDecimal,
            lon: BigDecimal,
        ) : Coordinates {
            return Coordinates(
                lat,
                lon
            )
        }
    }
}