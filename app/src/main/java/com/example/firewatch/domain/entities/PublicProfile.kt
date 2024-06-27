package com.example.firewatch.domain.entities

import androidx.room.Embedded
import com.example.firewatch.domain.valueObjects.Phone

class PublicProfile(
    val email: String,
    val userName: String,
    val avatar: String,
    val nif: String,
    @Embedded val phone: Phone
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
