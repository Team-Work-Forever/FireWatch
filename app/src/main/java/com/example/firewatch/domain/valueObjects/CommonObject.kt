package com.example.firewatch.domain.valueObjects

import com.example.firewatch.shared.errors.DomainException

class CommonObject private constructor(val value: String){
    companion object {
        fun create(value: String, field: String): Result<CommonObject> {
            if (value.isEmpty()) {
                return Result.failure(DomainException("Please provide an $field"))
            }

            return Result.success(CommonObject(value))
        }
    }
}