package com.example.firewatch.domain.entities

import androidx.room.ColumnInfo
import com.example.firewatch.shared.models.BaseEntity
import com.example.firewatch.domain.valueObjects.UserType

open class IdentityUser protected constructor(
    id: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("user_type") val userType: UserType
) : BaseEntity(id) {
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
            )
        }
    }
}