package com.example.firewatch.domain.valueObjects

import com.example.firewatch.R
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.utils.TranslateUtil


class Email private constructor(val value: String) {
    companion object {
        private const val EMAIL_REGEX = """^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$"""

        fun create(value: String): Result<Email> {
            if (value.isEmpty()) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_email)))
            }

            if (!EMAIL_REGEX.toRegex().matches(value)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_valid_email)))
            }

            return Result.success(Email(value))
        }
    }
}