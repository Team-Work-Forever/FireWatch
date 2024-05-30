package com.example.firewatch.services.http.contracts

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Geometry(
    @SerializedName("coordinates") val coordinates: Array<BigDecimal>,
    @SerializedName("type") val type: String,
) {
    init {
        require(coordinates.size == 2) { "Coordinates array must contain exactly 2 elements" }
    }

    val lat: BigDecimal
        get() = coordinates[0]

    val lon: BigDecimal
        get() = coordinates[1]
}

data class GeoFeature<TResponse>(
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("properties") val properties: TResponse,
    @SerializedName("type") val type: String,
)