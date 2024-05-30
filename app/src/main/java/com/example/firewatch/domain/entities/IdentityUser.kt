package com.example.firewatch.domain.entities

import com.example.firewatch.shared.models.Entity
import com.example.firewatch.domain.valueObjects.UserType

open class IdentityUser protected constructor(
    id: String,
    val email: String,
    val userType: UserType
) : Entity(id) {
    companion object {
        fun create(
            id: String,
            email: String,
            userType: UserType
        ): IdentityUser {
        return IdentityUser(
            id,
            email,
            userType
        )}
    }
}