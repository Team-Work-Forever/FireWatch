package com.example.firewatch.context.auth.types

import com.example.firewatch.services.http.api.AuthApiService

abstract class Authentication {
    abstract suspend fun getTokens(api: AuthApiService): Result<Tokens>
}

//class NIFAuthentication(
//    val nif: String,
//    val password: String
//) : Authentication() {
//
//}

//class TokenAuthentication(
//    val refreshToken: String
//) : Authentication() {
//
//}