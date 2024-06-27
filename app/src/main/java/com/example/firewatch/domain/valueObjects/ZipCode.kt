package com.example.firewatch.domain.valueObjects

import com.example.firewatch.R
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.utils.TranslateUtil

class ZipCode private constructor(value: String) {
    companion object {
        private val ZIP_CODE_REGEX = Regex("^\\d{4}(-\\d{3})?\$")

        fun create(value: String): Result<ZipCode> {
            if (value.isEmpty()) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_zip_code)))
            }

            if (!ZIP_CODE_REGEX.matches(value)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.format_of_an_zip_code_4444_444)))
            }

            return Result.success(ZipCode(value))
        }
    }
}