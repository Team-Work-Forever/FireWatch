package com.example.firewatch.services.http.contracts.valueObjects

import com.example.firewatch.domain.valueObjects.Address
import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("city") val city: String,
    @SerializedName("number") val number: Int,
    @SerializedName("street") val street: String,
    @SerializedName("zip_code") val zipCode: ZipCodeResponse,
) {
    fun toAddress() : Address {
        return Address.create(
            this.street,
            this.number,
            this.zipCode.toZipCode(),
            this.city,
        ).getOrThrow()
    }
}