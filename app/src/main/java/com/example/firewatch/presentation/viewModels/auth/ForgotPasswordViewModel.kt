package com.example.firewatch.presentation.viewModels.auth

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.dtos.ResetPasswordInput
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.valueObjects.CommonObject
import com.example.firewatch.domain.valueObjects.Password
import com.example.firewatch.shared.extensions.addPasswordCheck
import com.example.firewatch.shared.extensions.addValidators
import com.example.firewatch.shared.extensions.canDo
import com.example.firewatch.shared.extensions.cannotDo
import com.example.firewatch.shared.extensions.getError
import com.example.firewatch.shared.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    val authUser: User? = authService.getIdentity<User>().getOrNull() ?: emptySlot

    companion object {
        var email: String = ""

        var emptySlot: User? = null
    }

    val forgotCode = MutableLiveData("")
    val forgotValidator = LiveDataValidator<CommonObject, String>(forgotCode).apply {
        addRule { CommonObject.create(it, "forgot")  }
    }

    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    val passwordValidator = LiveDataValidator<Password, String>(password).apply {
        addPasswordCheck(confirmPassword)
    }

    val confirmPasswordValidator = LiveDataValidator<Password, String>(confirmPassword).apply {
        addPasswordCheck(password)
    }

    val canStageOne = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            forgotValidator,
        ))
    }
    val canStageTwo = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            passwordValidator,
            confirmPasswordValidator
        ))
    }

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
            if (canStageOne.cannotDo() || canStageTwo.cannotDo()) {
                return@async Result.failure(Exception("Please provide valid values to perform this operation"))
            }

            val response = authService.resetPassword(ResetPasswordInput(
                forgotValidator.getValue(),
                passwordValidator.getValue(),
                confirmPasswordValidator.getValue()
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