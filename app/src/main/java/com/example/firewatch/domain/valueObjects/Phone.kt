package com.example.firewatch.domain.valueObjects

import androidx.room.ColumnInfo
import com.example.firewatch.R
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.utils.TranslateUtil

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

        fun empty(): Phone {
            return Phone("", "")
        }

        fun create(code: String, number: String): Result<Phone> {
            if (code.isEmpty() || number.isEmpty()) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.provide_an_valid_phone_number_or_code)))
            }

            if (!validateCountryCode(code)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.provide_an_valid_country_code)))
            }

            if (!shouldHaveLength(number, 9)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.the_phone_number_must_be_9_digits)))
            }

            return Result.success(Phone(code, number))
        }
    }
}