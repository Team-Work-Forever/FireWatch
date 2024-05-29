package com.example.firewatch.domain.valueObjects

class Phone private constructor(
    val countryCode: String,
    val number: String,
) {
    companion object {
        fun create(code: String, number: String): Phone {
            return Phone(code, number)
        }
    }
}