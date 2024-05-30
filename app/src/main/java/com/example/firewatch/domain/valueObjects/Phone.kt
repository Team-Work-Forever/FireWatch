package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo

class Phone private constructor(
    @ColumnInfo("code") val countryCode: String,
    @ColumnInfo("number") val number: String,
) {
    companion object {
        fun create(code: String, number: String): Phone {
            return Phone(code, number)
        }
    }
}