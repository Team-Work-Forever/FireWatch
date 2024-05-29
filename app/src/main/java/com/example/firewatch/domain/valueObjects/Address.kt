package com.example.firewatch.domain.valueObjects

class Address private constructor(
    val street: String,
    val number: Int,
    val zipCode: String,
    val city: String
){
    companion object {
        fun create(
            street: String,
            number: Int,
            zipCode: String,
            city: String
        ): Address {
            return Address(
                street,
                number,
                zipCode,
                city
            )
        }
    }
}