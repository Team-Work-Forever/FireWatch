package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo

class Address private constructor(
    @ColumnInfo("street") val street: String,
    @ColumnInfo("number") val number: Int,
    @ColumnInfo("zip_code") val zipCode: String,
    @ColumnInfo("city") val city: String
) {
    companion object {
        fun create(
            street: String,
            number: Int,
            zipCode: String,
            city: String
        ): Address {
            return Address(
                street,
                number,
                zipCode,
                city
            )
        }
    }
}