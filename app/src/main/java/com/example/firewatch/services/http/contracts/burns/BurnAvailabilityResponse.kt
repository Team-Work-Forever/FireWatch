package com.example.firewatch.services.http.contracts.burns

import com.google.gson.annotations.SerializedName

data class BurnAvailabilityResponse(
    @SerializedName("result") val result: Boolean
)