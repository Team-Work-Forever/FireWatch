package com.example.firewatch.services.persistence.converters

import androidx.room.TypeConverter
import com.example.firewatch.domain.valueObjects.BurnReason

class BurnReasonConverter {
    @TypeConverter
    fun fromString(value: String?): BurnReason? {
        return value?.let { BurnReason.get(it) }
    }

    @TypeConverter
    fun burnReasonToString(value: BurnReason?): String? {
        return value?.value
    }
}