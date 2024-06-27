package com.example.firewatch.domain.entities

import androidx.room.ColumnInfo
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.domain.valueObjects.UserType
import com.google.gson.annotations.SerializedName

class User(
    id: String,
    email: String,
    nif: String,
    @ColumnInfo("user_name") val userName: String,
    @ColumnInfo("first_name") val firstName: String,
    @ColumnInfo("last_name") val lastName: String,
    phone: Phone,
    address: Address,
    avatar: String,
    userType: UserType
) : BaseUser(id, email, nif, phone, address, avatar, userType) {
    companion object {
        fun newPublic(
            email: String,
            avatar: String,
            userName: String,
            nif: String,
            phone: Phone
        ): User {
            return User(
                "",
                email,
                nif,
                userName,
                userName,
                "",
                phone,
                Address.empty(),
                avatar,
                UserType.USER
            )
        }

        fun create(
            id: String,
            email: String,
            nif: String,
            userName: String,
            firstName: String,
            lastName: String,
            phone: Phone,
            address: Address,
            avatar: String,
            userType: UserType = UserType.USER,
        ): User {
            return User(
                id,
                email,
                nif,
                userName,
                firstName,
                lastName,
                phone,
                address,
                avatar,
                userType
            )
        }
    }

    override fun getFullName(): String = "$firstName $lastName"
}