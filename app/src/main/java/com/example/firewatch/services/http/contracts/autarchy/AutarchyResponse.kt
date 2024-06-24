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
    @SerializedName("nif") val nif: String,
    @SerializedName("phone") val phone: PhoneResponse,
    @SerializedName("address") val address: AddressResponse,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("total_of_burns") val totalBurns: Int,
) {
    fun toAutarchy(coordinates: Coordinates): Autarchy {
        return Autarchy.create(
            id,
            nif,
            email,
            title,
            coordinates,
            phone.toPhone(),
            address.toAddress(),
            avatar,
            totalBurns,
        )
    }
}