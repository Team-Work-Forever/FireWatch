package com.example.firewatch.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.example.firewatch.context.auth.types.Tokens
import com.example.firewatch.shared.models.BaseEntity
import com.example.firewatch.domain.valueObjects.UserType

open class IdentityUser(
    id: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("user_type") var userType: UserType
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

    open fun getFullName(): String = email
}