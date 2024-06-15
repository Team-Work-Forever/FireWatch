package com.example.firewatch.domain.valueObjects

import com.example.firewatch.shared.errors.DomainException

class NIF private constructor(value: String) {
    companion object {
        val NIF_REGEX = Regex("\\b[0-9]{9}\\b")

        fun create(value: String): Result<NIF> {
            if (value.isEmpty()) {
                return Result.failure(DomainException("Please provide an nif"))
            }

            if (!NIF_REGEX.matches(value)) {
                return Result.failure(DomainException("Please provide an valid nif"))
            }

            return Result.success(NIF(value))
        }
    }
}