package com.example.firewatch.shared.extensions

import com.example.firewatch.domain.valueObjects.Coordinates
import com.tomtom.sdk.location.GeoPoint
import java.math.BigDecimal

fun GeoPoint.toCoordinates(): Coordinates {
    return Coordinates(
        BigDecimal(latitude),
        BigDecimal(longitude)
    )
}