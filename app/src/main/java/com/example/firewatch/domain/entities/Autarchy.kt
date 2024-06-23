package com.example.firewatch.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.domain.valueObjects.UserType

abstract class BaseUser(
    id: String,
    email: String,
    @ColumnInfo("nif") val nif: String,
    @Embedded val phone: Phone,
    @Embedded val address: Address,
    @ColumnInfo("avatar") val avatar: String,
    userType: UserType
) : IdentityUser(id, email, userType) {

}

@Entity(tableName = "autarchies")
class Autarchy(
    id: String,
    email: String,
    @ColumnInfo("title") val title: String,
    nif: String,
    @Embedded val coordinates: Coordinates,
    phone: Phone,
    address: Address,
    avatar: String,
) : BaseUser(id, email, nif, phone, address, avatar, UserType.AUTARCHY) {
    companion object {
        fun create(
            id: String,
            nif: String,
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
                nif,
                coordinates,
                phone,
                address,
                avatar
            )
        }
    }

    override fun getFullName(): String = title
}