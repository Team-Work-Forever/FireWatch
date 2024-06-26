package com.example.firewatch.services.http.contracts.profile

import com.example.firewatch.domain.entities.PublicProfile
import com.example.firewatch.services.http.contracts.valueObjects.PhoneResponse
import com.google.gson.annotations.SerializedName

class PublicProfileResponse(
    @SerializedName("email") val email: String,
    @SerializedName("user_name") val userName: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("nif") val nif: String,
    @SerializedName("phone") val phone: PhoneResponse,
) {
    fun toPublicProfile(): PublicProfile {
        return PublicProfile(
            email,
            userName,
            avatar,
            nif,
            phone.toPhone()
        )
    }
}