package com.example.firewatch.services.persistence.converters

import androidx.room.TypeConverter
import com.example.firewatch.domain.valueObjects.BurnType

class BurnTypeConverter {
    @TypeConverter
    fun fromString(value: String?): BurnType? {
        return value?.let { BurnType.get(it) }
    }

    @TypeConverter
    fun burnTypeToString(value: BurnType?): String? {
        return value?.value
    }
}