package com.example.firewatch.data.entities

import com.example.firewatch.data.valueObjects.Address
import com.example.firewatch.data.valueObjects.Phone

class User private constructor(
    val email: String,
    val userName: String,
    val lastName: String,
    val phone: Phone,
    val address: Address,
    val userType: String
) {
    companion object {
        fun create(
            email: String,
            userName: String,
            lastName: String,
            phone: Phone,
            address: Address,
            userType: String
        ): User {
        return User(
            email,
            userName,
            lastName,
            phone,
            address,
            userType
        )}
    }
}