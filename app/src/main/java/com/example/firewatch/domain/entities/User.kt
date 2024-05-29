package com.example.firewatch.domain.entities

import com.example.firewatch.domain.shared.Entity
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone

class User private constructor(
    val email: String,
    val avatar: String,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val phone: Phone,
    val address: Address,
    val userType: String
) : Entity() {
    companion object {
        fun create(
            email: String,
            avatar: String,
            userName: String,
            firstName: String,
            lastName: String,
            phone: Phone,
            address: Address,
            userType: String
        ): User {
        return User(
            email,
            avatar,
            userName,
            firstName,
            lastName,
            phone,
            address,
            userType
        )}
    }
}