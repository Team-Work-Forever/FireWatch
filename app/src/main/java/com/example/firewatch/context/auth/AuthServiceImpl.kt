package com.example.firewatch.context.auth

import android.content.Context
import com.example.firewatch.context.auth.dtos.ResetPasswordInput
import com.example.firewatch.context.auth.dtos.SignUpInput
import com.example.firewatch.context.auth.types.Authentication
import com.example.firewatch.context.auth.types.TokenAuthentication
import com.example.firewatch.context.auth.types.Tokens
import com.example.firewatch.domain.entities.IdentityUser
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.api.AuthApiService
import com.example.firewatch.services.http.contracts.auth.ResetPasswordRequest
import com.example.firewatch.services.http.interceptiors.AuthorizationInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Suppress("UNCHECKED_CAST")
class AuthServiceImpl(
    httpService: HttpService,
    authenticationInterceptor: AuthorizationInterceptor,
    private val profileRepository: ProfileRepository,
) : AuthService {
    private val authApi: AuthApiService = httpService.authService

    companion object {
        var tokens: Pair<String, String>? = null
        private var identityUser: IdentityUser? = null
    }

    init {
        authenticationInterceptor.setAuthService(this)
    }

    private fun clearAuthRules() {
        tokens = null
        identityUser = null
    }

    private suspend fun setTokens(authTokens: Tokens) {
        tokens = Pair(authTokens.accessToken, authTokens.refreshToken)

        val profileResponse = profileRepository.getInfo()

        if (profileResponse.isFailure) {
            return clearAuthRules()
        }

        identityUser = profileResponse.getOrThrow()
    }

    override suspend fun authentication(authentication: Authentication): Result<Boolean> {
        val tokensResult = authentication.getTokens(authApi)

        if (tokensResult.isFailure) {
            return failure(tokensResult.exceptionOrNull()!!)
        }

        val tokens = tokensResult.getOrThrow()
        setTokens(tokens)

        return success(tokensResult.isSuccess)
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
            val response = HttpService.fetch {
                authApi.resetPassword(
                    input.forgotToken,
                    ResetPasswordRequest(
                        input.password,
                        input.confirmPassword
                    )
                )
            }

            val result = response.getOrThrow()
            success(result.message)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override suspend fun signUp(input: SignUpInput): Result<String> {
        return try {
            val response = HttpService.fetch {
                authApi.signUp(input.toMultipart())
            }

            val tokens = response.getOrThrow()
            setTokens(Tokens(tokens.accessToken, tokens.refreshToken))
            success(tokens.accessToken)
        } catch (e: Exception) {
            failure(e)
        }
    }

    override fun logout(): Boolean {
        clearAuthRules()

        return true
    }

    override fun <TUserType : IdentityUser> getIdentity(): Result<TUserType> {
        val user = identityUser as? TUserType

        user?.let {
            return success(it)
        }

        return failure(AuthException("Currently there is no user authenticated"))
    }

    override fun getDefaultIdentify(): Result<IdentityUser> {
        val user = identityUser

        user?.let {
            return success(it)
        }

        return failure(AuthException("Currently there is no user authenticated"))
    }

    override fun getActiveTokens(): Tokens? {
        tokens?.apply {
            return Tokens(first, second)
        }

        return null
    }

    override suspend fun fetchProfile(): Result<Boolean> {
        tokens?.let {
            return success(authentication(TokenAuthentication(it.second)).isSuccess)
        }

        return failure(AuthException("Failed to fetch profile"))
    }
}
