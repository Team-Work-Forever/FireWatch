package com.example.firewatch.services.http.contracts.valueObjects

import com.example.firewatch.domain.valueObjects.Phone
import com.google.gson.annotations.SerializedName

data class PhoneResponse(
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("number") val number: String,
) {
    fun toPhone() : Phone {
        return Phone.create(
            this.countryCode,
            this.number
        )
    }
}