package com.example.firewatch.services.persistence.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {
    @TypeConverter
    fun fromString(value: String?): BigDecimal? {
        return value?.let { BigDecimal(value) }
    }

    @TypeConverter
    fun bigDecimalToString(value: BigDecimal?): String? {
        return value?.toPlainString()
    }
}