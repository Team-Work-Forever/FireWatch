package com.example.firewatch.shared.extensions


fun Result<*>.getError(): Throwable {
    if (this.isFailure) {
        return this.exceptionOrNull()!!
    }

    throw Exception("There is no exception")
}

fun Result<*>.getProblem(): String {
    if (this.isFailure) {
        return this.exceptionOrNull()?.message!!
    }

    return "there is no problem"
}