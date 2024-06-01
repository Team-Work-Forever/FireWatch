package com.example.firewatch.presentation.components

enum class TextFieldType(val id: Int) {
    NORMAL(0),
    PASSWORD(1),
    EMAIL(2);

    companion object {
        fun get(id: Int): TextFieldType? {
            return TextFieldType.entries.find { it.id == id }
        }
    }
}