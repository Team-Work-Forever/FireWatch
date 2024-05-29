package com.example.firewatch.services.http.contracts.profile

import com.example.firewatch.domain.entities.User
import com.example.firewatch.services.http.contracts.valueObjects.AddressResponse
import com.example.firewatch.services.http.contracts.valueObjects.PhoneResponse
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("email") val email: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("user_name") val userName: String,
    @SerializedName("phone") val phone: PhoneResponse,
    @SerializedName("address") val address: AddressResponse,
    @SerializedName("user_type") val userType: String,
) {
    fun toUser(): User {
        return User.create(
            this.email,
            this.avatar,
            this.userName,
            this.firstName,
            this.lastName,
            this.phone.toPhone(),
            this.address.toAddress(),
            this.userType,
            )
    }
}