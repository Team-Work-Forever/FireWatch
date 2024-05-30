package com.example.firewatch.services.http.contracts.autarchy

import com.google.gson.annotations.SerializedName

data class DeleteAutarchyResponse(
    @SerializedName("autarchy_id") val autarchyId: String
)