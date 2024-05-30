package com.example.firewatch.domain.valueObjects

enum class BurnType(val value: String) {
    BURN("burn");

    companion object {
        fun get(value: String): BurnType? {
            return BurnType.entries.find { it.value == value }
        }
    }
}