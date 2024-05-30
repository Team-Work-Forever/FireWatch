package com.example.firewatch.shared.errors

class BurnReasonNotExists(burnReason: String)
    : IllegalArgumentException("The BurnReason $burnReason does not exists") {
}