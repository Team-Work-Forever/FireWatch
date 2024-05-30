package com.example.firewatch.services.persistence.converters

import androidx.room.TypeConverter
import com.example.firewatch.domain.valueObjects.BurnState

class BurnStateConverter {
    @TypeConverter
    fun fromString(value: String?): BurnState? {
        return value?.let { BurnState.get(it) }
    }

    @TypeConverter
    fun burnStateToString(value: BurnState?): String? {
        return value?.state
    }
}