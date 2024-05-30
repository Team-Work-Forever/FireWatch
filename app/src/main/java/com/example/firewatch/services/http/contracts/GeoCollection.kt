package com.example.firewatch.services.http.contracts

import com.google.gson.annotations.SerializedName

data class GeoCollection<TResponse>(
    @SerializedName("type") val type: String,
    @SerializedName("features") val features: List<GeoFeature<TResponse>>,
    @SerializedName("pagination") val pagination: Pagination,
)