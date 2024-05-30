package com.example.firewatch.shared.errors

class BurnTypeNotExists(burnState: String)
    : IllegalArgumentException("The BurnType $burnState does not exists") {
}