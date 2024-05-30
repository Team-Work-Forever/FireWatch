package com.example.firewatch.shared.utils

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    private const val DATE_PATTERN = "yyyy-MM-dd"

    fun convertFromISO(zonedTime: String): LocalDateTime {
        return ZonedDateTime.parse(zonedTime, DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime()
    }

    fun toString(zonedTime: LocalDateTime): String {
        return zonedTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN))
    }
}