package com.example.firewatch.context

import com.example.firewatch.context.dtos.ResetPasswordInput
import com.example.firewatch.context.dtos.SignUpInput
import com.example.firewatch.data.entities.User
import com.example.firewatch.data.valueObjects.Address
import com.example.firewatch.data.valueObjects.Phone
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.api.AuthApiService
import com.example.firewatch.services.http.contracts.auth.LoginRequest
import com.example.firewatch.services.http.contracts.auth.ResetPasswordRequest
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class AuthServiceImpl(
    private val authApi: AuthApiService
) : AuthService {
    companion object {
        private var tokens: Pair<String, String>? = null
    }

    private fun setTokens(accessToken: String, refreshToken: String) {
        tokens = Pair(accessToken, refreshToken)
    }

    override suspend fun forgotPassword(email: String): Result<String> {
        return try {
            val response = authApi.forgotPassword(email)

            if (!response.isSuccessful) {
                failure<String>(AuthException(response.errorBody()!!.string()))
            }

            var result = response.body()!!
            success(result.message)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override suspend fun resetPassword(input: ResetPasswordInput): Result<String> {
        return try {
            val response = authApi.resetPassword(
                input.forgotToken,
                ResetPasswordRequest(
                    input.password,
                    input.confirmPassword
                )
            )

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()!!.string()
                failure<String>(AuthException(errorBody))
            }

            var result = response.body()!!
            success(result.message)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = authApi.login(LoginRequest(email, password))

            if (!response.isSuccessful) {
                failure<String>(AuthException(response.errorBody()!!.string()))
            }

            val tokens = response.body()!!
            setTokens(tokens.accessToken, tokens.refreshToken)
            success(tokens.accessToken)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override suspend fun signUp(input: SignUpInput): Result<String> {
        return try {
            val response = authApi.signUp(input.getResponseBody())

            if (!response.isSuccessful) {
                failure<String>(AuthException(response.errorBody()!!.string()))
            }

            val tokens = response.body()!!
            setTokens(tokens.accessToken, tokens.refreshToken)
            success(tokens.accessToken)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override fun getIdentity(): User {
        return User.create(
            "",
            "",
            "",
            Phone.create("", ""),
            Address.create(
                "",
                0,
                "",
                "",
            ),
            ""
        )
    }

    override suspend fun checkAuth(value: String): Result<String> {
        return try {
            val response = authApi.refreshTokens(value)

            if (!response.isSuccessful) {
                failure<String>(AuthException(response.errorBody()!!.string()))
            }

            val tokens = response.body()!!
            setTokens(tokens.accessToken, tokens.refreshToken)
            success(tokens.accessToken)
        } catch (e: Exception) {
            failure(e)
        }
    }
}
