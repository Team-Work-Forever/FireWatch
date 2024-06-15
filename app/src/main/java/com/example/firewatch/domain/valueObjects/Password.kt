package com.example.firewatch.domain.valueObjects

import com.example.firewatch.shared.errors.DomainException
import java.util.regex.Pattern

class Password private constructor(val value: String) {
    companion object {
        private val NUMBER_REGEX = Pattern.compile("[0-9]")
        private val UPPER_CASE_REGEX = Pattern.compile("[A-Z]")
        private val LOWER_CASE_REGEX = Pattern.compile("[a-z]")

        private fun containsNumber(s: String): Boolean {
             return NUMBER_REGEX.matcher(s).find()
         }

        private fun containsCapitalLetter(s: String): Boolean {
             return UPPER_CASE_REGEX.matcher(s).find()
         }

        private fun containsNonCapitalLetter(s: String): Boolean {
             return LOWER_CASE_REGEX.matcher(s).find()
        }

        fun checkPassword(password: String, confirmPassword: String?): Result<Password> {
            if (password == confirmPassword) {
                return create(password)
            }

            return Result.failure(DomainException("Passwords are not the same"))
        }

        fun create(value: String): Result<Password> {
            if (value.isEmpty()) {
                return Result.failure(DomainException("Please Provide an Password"))
            }

            if (!containsCapitalLetter(value)) {
                return Result.failure(DomainException("Password must contain at least one uppercase letter"))
            }

            if (!containsNonCapitalLetter(value)) {
                return Result.failure(DomainException("Password must contain at least one lowercase letter"))
            }

            if (!containsNumber(value)) {
                return Result.failure(DomainException("Password must contain at least one digit"))
            }

            if (value.length !in 6..16) {
                return Result.failure(DomainException("Password must be between 6 and 16 characters long"))
            }

            return Result.success(Password(value))
        }
    }
}