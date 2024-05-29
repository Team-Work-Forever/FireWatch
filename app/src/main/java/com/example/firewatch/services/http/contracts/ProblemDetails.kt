package com.example.firewatch.services.http.contracts

import com.google.gson.annotations.SerializedName

data class ProblemDetailsResponse(
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("status") val status: Int,
    @SerializedName("detail") val detail: String,
    @SerializedName("instance") val instance: String,
)