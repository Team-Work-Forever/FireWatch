package com.example.firewatch.domain.valueObjects

import com.example.firewatch.shared.errors.DomainException

class ZipCode private constructor(value: String) {
    companion object {
        private val ZIP_CODE_REGEX = Regex("^\\d{4}(-\\d{3})?\$")

        fun create(value: String): Result<ZipCode> {
            if (value.isEmpty()) {
                return Result.failure(DomainException("Please provide an zip code"))
            }

            if (!ZIP_CODE_REGEX.matches(value)) {
                return Result.failure(DomainException("Format of an zip code 4444-444"))
            }

            return Result.success(ZipCode(value))
        }
    }
}