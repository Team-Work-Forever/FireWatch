package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo
import com.example.firewatch.shared.errors.DomainException

class Phone(
    @ColumnInfo("phone_code") val countryCode: String,
    @ColumnInfo("phone_number") val number: String,
) {
    companion object {
        private fun shouldHaveLength(value: String, length: Int): Boolean {
            return value.length == length
        }
        private fun validateCountryCode(code: String): Boolean {
            return code.contains("+") && code.length <= 4
        }
        fun create(code: String, number: String): Result<Phone> {
            if (code.isEmpty() || number.isEmpty()) {
                return Result.failure(DomainException("Provide an valid phone number or code"))
            }

            if (!validateCountryCode(code)) {
                return Result.failure(DomainException("Provide an valid country code"))
            }

            if (!shouldHaveLength(number, 9)) {
                return Result.failure(DomainException("The Phone number must be 9 digits"))
            }

            return Result.success(Phone(code, number))
        }
    }
}