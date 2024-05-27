package com.example.firewatch.context

import com.example.firewatch.context.dtos.SignUpInput
import com.example.firewatch.data.entities.User
import com.example.firewatch.data.valueObjects.Address
import com.example.firewatch.data.valueObjects.Phone
import com.example.firewatch.services.http.HttpService
import com.example.firewatch.services.http.contracts.auth.AuthResponse
import com.example.firewatch.services.http.contracts.auth.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthServiceImpl(private val httpService: HttpService) : AuthService {
    companion object {
        private var tokens: Pair<String, String>? = null
    }

    private fun setTokens(accessToken: String, refreshToken: String) {
        tokens = Pair(accessToken, refreshToken)
    }

    override suspend fun login(email: String, password: String): Boolean {
        httpService.authService.login(LoginRequest(email, password))
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (!response.isSuccessful) {
                        throw AuthException(response.errorBody()?.string() ?: "")
                    }

                    val loginResponse: AuthResponse = response.body()
                        ?: throw AuthException("Unvalid response recived")

                    setTokens(loginResponse.accessToken, loginResponse.refreshToken)
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    throw AuthException("Error Login into the system")
                }
            })

        return true
    }

    override suspend fun signUp(input: SignUpInput): Boolean {
        httpService.authService.signUp(input.getResponseBody()).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                if (!response.isSuccessful) {
                    throw AuthException(response.errorBody()?.string() ?: "")
                }

                val signUpResponse = response.body()
                    ?: throw AuthException("Unvalid response recived")

                setTokens(signUpResponse.accessToken, signUpResponse.refreshToken)
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                throw AuthException("Error signUp into the system")
            }
        })

        return true
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

    override suspend fun checkAuth(): Boolean {
        TODO("Not yet implemented")
    }
}
