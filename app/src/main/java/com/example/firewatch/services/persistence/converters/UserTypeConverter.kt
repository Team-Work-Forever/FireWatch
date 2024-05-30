package com.example.firewatch.services.persistence.converters

import androidx.room.TypeConverter
import com.example.firewatch.domain.valueObjects.UserType

class UserTypeConverter {
    @TypeConverter
    fun fromString(value: String?): UserType? {
        return value?.let { UserType.get(it) }
    }

    @TypeConverter
    fun userTypeToString(value: UserType?): String? {
        return value?.type
    }
}
