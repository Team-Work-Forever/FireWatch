package com.example.firewatch.data.valueObjects

class Phone private constructor(
    countryCode: String,
    number: String,
) {
    companion object {
        fun create(code: String, number: String): Phone {
            return Phone(code, number)
        }
    }
}