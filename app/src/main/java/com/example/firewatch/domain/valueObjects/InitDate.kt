package com.example.firewatch.domain.valueObjects

import com.example.firewatch.shared.errors.DomainException
import java.time.LocalDateTime

class InitDate private constructor(val value: LocalDateTime) {
    companion object {
        fun create(value: LocalDateTime, start: LocalDateTime = LocalDateTime.now()): Result<InitDate> {
            if (start.isAfter(value)) {
                return Result.failure(DomainException("please provide an possible date"))
            }

            return Result.success(InitDate(value))
        }
    }
}