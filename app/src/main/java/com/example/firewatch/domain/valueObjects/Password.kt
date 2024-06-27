package com.example.firewatch.domain.valueObjects

import com.example.firewatch.R
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.utils.TranslateUtil
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

            return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.passwords_are_not_the_same)))
        }

        fun create(value: String): Result<Password> {
            if (value.isEmpty()) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_password)))
            }

            if (!containsCapitalLetter(value)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.password_must_contain_at_least_one_uppercase_letter)))
            }

            if (!containsNonCapitalLetter(value)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.password_must_contain_at_least_one_lowercase_letter)))
            }

            if (!containsNumber(value)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.password_must_contain_at_least_one_digit)))
            }

            if (value.length !in 6..16) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.password_must_be_between_6_and_16_characters_long)))
            }

            return Result.success(Password(value))
        }
    }
}