package com.example.firewatch.context.auth

import com.example.firewatch.context.auth.dtos.ResetPasswordInput
import com.example.firewatch.context.auth.dtos.SignUpInput
import com.example.firewatch.domain.entities.IdentityUser
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.api.AuthApiService
import com.example.firewatch.services.http.contracts.auth.LoginRequest
import com.example.firewatch.services.http.contracts.auth.ResetPasswordRequest
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Suppress("UNCHECKED_CAST")
class AuthServiceImpl(
    private val authApi: AuthApiService,
    private val profileRepository: ProfileRepository,
) : AuthService {
    companion object {
        private var tokens: Pair<String, String>? = null
        private var identityUser: IdentityUser? = null
    }

    private fun clearAuthRules() {
        tokens = null
        identityUser = null
    }

    private suspend fun setTokens(accessToken: String, refreshToken: String) {
        tokens = Pair(accessToken, refreshToken)

        val profileResponse = profileRepository.getInfo()

        if (profileResponse.isFailure) {
            return clearAuthRules()
        }

        identityUser = profileResponse.getOrThrow()
    }

    override suspend fun forgotPassword(email: String): Result<String> {
        return try {
            val response = authApi.forgotPassword(email)

            if (!response.isSuccessful) {
                failure<String>(AuthException(response.errorBody()!!.string()))
            }

            val result = response.body()!!
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

            val result = response.body()!!
            success(result.message)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<String> = try
    {
        val response = HttpService.fetch {
            authApi.login(LoginRequest(email, password))
        }

        val tokens = response.getOrThrow()
        setTokens(tokens.accessToken, tokens.refreshToken)
        success(tokens.accessToken)
    } catch (e: Exception) {
        failure(e)
    }

    override suspend fun signUp(input: SignUpInput): Result<String> {
        return try {
            val response = HttpService.fetch {
                authApi.signUp(input.toMultipart())
            }

            val tokens = response.getOrThrow()
            setTokens(tokens.accessToken, tokens.refreshToken)
            success(tokens.accessToken)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override fun <TUserType : IdentityUser> getIdentity(): Result<TUserType> {
        val user = identityUser as? TUserType

        user?.let {
            return success(it)
        }

        return failure(AuthException("Currently there is no user authenticated"))
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
