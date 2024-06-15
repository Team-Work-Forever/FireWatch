package com.example.firewatch.domain.valueObjects

import com.example.firewatch.shared.errors.DomainException


class Email private constructor(val value: String) {
    companion object {
        private const val EMAIL_REGEX = """^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$"""

        fun create(value: String): Result<Email> {
            if (value.isEmpty()) {
                return Result.failure(DomainException("Please provide an email"))
            }

            if (!EMAIL_REGEX.toRegex().matches(value)) {
                return Result.failure(DomainException("Please provide an valid Email"))
            }

            return Result.success(Email(value))
        }
    }
}