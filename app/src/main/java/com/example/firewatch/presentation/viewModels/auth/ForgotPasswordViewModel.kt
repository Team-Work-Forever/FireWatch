package com.example.firewatch.presentation.viewModels.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.User
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
    val authUser: User? = authService.getIdentity<User>().getOrNull()

    companion object {
        var email: String = ""
    }

    val forgotCode = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    fun sendForgotNotice(): Deferred<Boolean> {
        return viewModelScope.async {
            if (email.isEmpty()) return@async false

            val response = authService.forgotPassword(email)

            withContext(Dispatchers.Main) {
                return@withContext response.isSuccess
            }
        }
    }
}