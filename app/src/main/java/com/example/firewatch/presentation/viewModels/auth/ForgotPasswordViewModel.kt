package com.example.firewatch.presentation.viewModels.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.dtos.ResetPasswordInput
import com.example.firewatch.domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    val authUser: User? = authService.getIdentity<User>().getOrNull()

    companion object {
        var email: String = ""
    }

    val forgotCode = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    fun sendForgotNotice(): Deferred<Result<String>> {
        return viewModelScope.async {
            if (email.isEmpty()) {
                return@async Result.failure(Exception("Please provide an email"))
            }

            val response = authService.forgotPassword(email)

            withContext(Dispatchers.Main) {
               if (response.isFailure) {
                    return@withContext  Result.failure(Exception("Failed to send the request"))
               }

                return@withContext Result.success(response.getOrThrow())
            }
        }
    }

    fun resetPassword(): Deferred<Result<String>> {
        return viewModelScope.async(Dispatchers.IO) {
            val passwordValue = password.value
            val confirmPasswordValue = confirmPassword.value
            val code = forgotCode.value
                ?: return@async Result.failure(Exception("Please provide an valid code"))

            if (passwordValue == null || confirmPasswordValue == null) {
                return@async Result.failure(Exception("Please provide an valid password"))
            }

            if (passwordValue != confirmPasswordValue) {
                return@async Result.failure(Exception("Please put the password and confirmPassword the same"))
            }

            val response = authService.resetPassword(ResetPasswordInput(
                code,
                passwordValue,
                confirmPasswordValue
            ))

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    return@withContext Result.failure(Exception(response.exceptionOrNull()?.message))
                }

                return@withContext Result.success(response.getOrThrow())
            }
        }
    }
}