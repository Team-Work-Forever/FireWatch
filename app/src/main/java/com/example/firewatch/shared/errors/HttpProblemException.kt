package com.example.firewatch.shared.errors

import com.example.firewatch.services.http.contracts.ProblemDetails

class HttpProblemException(val problem: ProblemDetails) : Exception() {
    override val message: String
        get() = problem.detail
}
