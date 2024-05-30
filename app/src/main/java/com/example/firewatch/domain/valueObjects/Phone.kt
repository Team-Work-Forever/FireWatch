package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo

class Phone(
    @ColumnInfo("phone_code") val countryCode: String,
    @ColumnInfo("phone_number") val number: String,
) {
    companion object {
        fun create(code: String, number: String): Phone {
            return Phone(code, number)
        }
    }
}