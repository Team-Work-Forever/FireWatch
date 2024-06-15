package com.example.firewatch.context.auth.types

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import java.time.LocalDate
import java.time.ZoneId

class Tokens(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun isValidToken(token: String): Boolean {
            val currentDate = LocalDate.now()

            if (token.isEmpty()) {
                return false
            }

            val decode: DecodedJWT = JWT.decode(token)
            val expirationDate = decode.expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

            return !expirationDate.isBefore(currentDate)
        }
    }
}