package com.example.firewatch.domain.entities

import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.domain.valueObjects.UserType

class User(
    id: String,
    email: String,
    val nif: String,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val phone: Phone,
    val address: Address,
    val avatar: String,
    userType: UserType
) : IdentityUser(id, email, userType) {
    companion object {
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