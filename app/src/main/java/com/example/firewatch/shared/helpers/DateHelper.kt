package com.example.firewatch.shared.helpers

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class DateHelper {
    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"

        fun getFormattedDate(year: Int, month: Int, dayOfMonth: Int): String {
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month + 1, dayOfMonth)
            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

            return dateFormat.format(selectedDate.time)
        }

        fun getLocalDateTime(year: Int, month: Int, dayOfMonth: Int): LocalDateTime {
            return LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
        }
    }
}