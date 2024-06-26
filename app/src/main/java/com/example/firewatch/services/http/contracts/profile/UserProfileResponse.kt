package com.example.firewatch.services.http.contracts.profile

import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.entities.IdentityUser
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.UserType
import com.example.firewatch.services.http.contracts.valueObjects.AddressResponse
import com.example.firewatch.services.http.contracts.valueObjects.PhoneResponse
import com.example.firewatch.shared.errors.UserTypeNotExists
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

abstract class ProfileResultResponse(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("nif") val nif: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("phone") val phone: PhoneResponse,
    @SerializedName("address") val address: AddressResponse,
    @SerializedName("user_type") val userType: String,
) {
    abstract fun toIdentityUser(): IdentityUser
}

class AutarchyProfileResponse(
    id: String,
    email: String,
    nif: String,
    avatar: String,
    @SerializedName("title") val title: String,
    phone: PhoneResponse,
    address: AddressResponse,
    userType: String,
    @SerializedName("total_of_burns") val totalBurns: Int,
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String,
) : ProfileResultResponse(id, email, nif, avatar, phone, address, userType) {
    override fun toIdentityUser(): Autarchy {
        return Autarchy.create(
            id,
            nif,
            email,
            title,
            Coordinates.new(
                BigDecimal(lat),
                BigDecimal(lon)
            ),
            phone.toPhone(),
            address.toAddress(),
            avatar,
            totalBurns
        )
    }
}

class UserProfileResponse(
    id: String,
    email: String,
    nif: String,
    avatar: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("user_name") val userName: String,
    phone: PhoneResponse,
    address: AddressResponse,
    userType: String,
) : ProfileResultResponse(id, email, nif, avatar, phone, address, userType) {
    override fun toIdentityUser(): User {
         val userType = UserType.get(this.userType) ?: throw UserTypeNotExists(this.userType)

        return User.create(
            this.id,
            this.email,
            nif,
            this.userName,
            this.firstName,
            this.lastName,
            this.phone.toPhone(),
            this.address.toAddress(),
            avatar,
            userType,
        )
    }
}