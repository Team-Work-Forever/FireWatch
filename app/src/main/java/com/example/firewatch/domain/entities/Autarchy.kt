package com.example.firewatch.domain.entities

import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.domain.valueObjects.UserType

class Autarchy private constructor(
    id: String,
    email: String,
    val title: String,
    val phone: Phone,
    val address: Address,
    val avatar: String,
) : IdentityUser(id, email, UserType.AUTARCHY) {
    companion object {
        fun create(
            id: String,
            email: String,
            title: String,
            phone: Phone,
            address: Address,
            avatar: String
        ) : Autarchy {
            return Autarchy(
                id,
                email,
                title,
                phone,
                address,
                avatar
            )
        }
    }
}