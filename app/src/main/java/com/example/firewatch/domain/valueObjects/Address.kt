package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo

class Address(
    @ColumnInfo("address_street") val street: String,
    @ColumnInfo("address_number") val number: Int,
    @ColumnInfo("address_zip_code") val zipCode: String,
    @ColumnInfo("address_city") val city: String
) {
    override fun toString(): String {
        return "$street, $number $zipCode $city"
    }

    companion object {
        fun create(
            street: String,
            number: Int,
            zipCode: String,
            city: String
        ): Result<Address> {
            return Result.success(Address(
                street,
                number,
                zipCode,
                city
            ))
        }
    }
}