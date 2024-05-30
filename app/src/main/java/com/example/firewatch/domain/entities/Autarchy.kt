package com.example.firewatch.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.domain.valueObjects.UserType

@Entity(tableName = "autarchies")
class Autarchy private constructor(
    id: String,
    email: String,
    @ColumnInfo("title") val title: String,
    @Embedded val coordinates: Coordinates,
    @Embedded val phone: Phone,
    @Embedded val address: Address,
    @ColumnInfo("avatar") val avatar: String,
) : IdentityUser(id, email, UserType.AUTARCHY) {
    companion object {
        fun create(
            id: String,
            email: String,
            title: String,
            coordinates: Coordinates,
            phone: Phone,
            address: Address,
            avatar: String
        ): Autarchy {
            return Autarchy(
                id,
                email,
                title,
                coordinates,
                phone,
                address,
                avatar
            )
        }
    }
}