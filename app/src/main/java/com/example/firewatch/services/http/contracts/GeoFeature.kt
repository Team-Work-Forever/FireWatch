package com.example.firewatch.services.http.contracts

import com.example.firewatch.domain.valueObjects.Coordinates
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

    fun getCoordinate(): Coordinates = Coordinates.new(lat, lon)
}

data class GeoFeature<TResponse>(
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("properties") val properties: TResponse,
    @SerializedName("type") val type: String,
)