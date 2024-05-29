package com.example.firewatch.services.http.contracts.valueObjects

import com.google.gson.annotations.SerializedName

data class ZipCodeResponse(
    @SerializedName("value") val value: String
) {
    fun toZipCode() : String {
        return value
    }
}