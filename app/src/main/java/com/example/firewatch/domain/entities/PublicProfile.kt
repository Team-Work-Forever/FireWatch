package com.example.firewatch.domain.entities

import com.example.firewatch.domain.valueObjects.Phone

class PublicProfile(
    val email: String,
    val userName: String,
    val avatar: String,
    val nif: String,
    val phone: Phone
) {
    fun toUser(): User {
        return User.newPublic(
            email,
            avatar,
            userName,
            nif,
            phone
        )
    }
}
