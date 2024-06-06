package com.example.firewatch.shared.extensions

fun Result<*>.getProblem(): String {
    if (this.isFailure) {
        return this.exceptionOrNull()?.message!!
    }

    return "there is no problem"
}