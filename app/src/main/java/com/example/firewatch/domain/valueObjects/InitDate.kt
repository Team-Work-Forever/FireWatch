package com.example.firewatch.domain.valueObjects

import com.example.firewatch.R
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.utils.TranslateUtil
import java.time.LocalDateTime

class InitDate private constructor(val value: LocalDateTime) {
    companion object {
        fun create(value: LocalDateTime, start: LocalDateTime = LocalDateTime.now().minusDays(1L)): Result<InitDate> {
            if (start.isAfter(value)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_possible_date)))
            }

            return Result.success(InitDate(value))
        }

        fun createAfter(value: LocalDateTime, start: LocalDateTime?): Result<InitDate> {
            if (start == null) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_possible_date)))
            }

            if (start.isBefore(value)) {
                return Result.failure(DomainException(TranslateUtil.context!!.getString(R.string.please_provide_an_possible_date)))
            }

            return Result.success(InitDate(value))
        }
    }
}