package com.example.firewatch.domain.valueObjects

enum class BurnState(val state: String) {
    SCHEDULED("scheduled"),
    ACTIVE("active"),
    COMPLETED("completed"),
    REJECTED("rejected");

    companion object {
        fun get(value: String): BurnState? {
            return BurnState.entries.find { it.state == value }
        }
    }}