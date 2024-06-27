package com.example.firewatch.domain.valueObjects

import com.example.firewatch.R
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.utils.TranslateUtil

class NIF private constructor(value: String) {
    companion object {
        val NIF_REGEX = Regex("\\b[0-9]{9}\\b")

        fun create(value: String): Result<NIF> {
            if (value.isEmpty()) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_nif)))
            }

            if (!NIF_REGEX.matches(value)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_valid_nif)))
            }

            return Result.success(NIF(value))
        }
    }
}