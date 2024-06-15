package com.example.firewatch.services.http.contracts.autarchy

import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.services.http.contracts.valueObjects.AddressResponse
import com.example.firewatch.services.http.contracts.valueObjects.PhoneResponse
import com.google.gson.annotations.SerializedName

data class AutarchyResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: PhoneResponse,
    @SerializedName("address") val address: AddressResponse,
    @SerializedName("avatar") val avatar: String,
) {
    fun toAutarchy(coordinates: Coordinates): Autarchy {
        return Autarchy.create(
            id,
            "",
            email,
            title,
            coordinates,
            phone.toPhone(),
            address.toAddress(),
            avatar,
        )
    }
}