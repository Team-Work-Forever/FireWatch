package com.example.firewatch.shared.utils

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    fun convertFromISO(zonedTime: String): LocalDateTime {
        return ZonedDateTime.parse(zonedTime, DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime()
    }
}