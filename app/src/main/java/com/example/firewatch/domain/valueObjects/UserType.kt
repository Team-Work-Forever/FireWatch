package com.example.firewatch.domain.valueObjects

enum class UserType(val type: String) {
    USER("user"),
    AUTARCHY("autarchy"),
    ADMIN("admin");

    companion object {
        fun get(value: String): UserType? {
            return entries.find { it.type == value }
        }
    }
}