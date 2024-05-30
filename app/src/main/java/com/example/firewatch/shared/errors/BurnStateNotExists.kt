package com.example.firewatch.shared.errors

class BurnStateNotExists(burnState: String)
    : IllegalArgumentException("The BurnState $burnState does not exists") {
}