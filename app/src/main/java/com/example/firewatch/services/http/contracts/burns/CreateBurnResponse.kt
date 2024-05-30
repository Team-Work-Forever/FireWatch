package com.example.firewatch.services.http.contracts.burns

import com.google.gson.annotations.SerializedName

data class CreateBurnResponse(
    @SerializedName("burn_id") val burnId: String,
    @SerializedName("state") val state: String,
)