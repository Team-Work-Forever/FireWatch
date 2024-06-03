package com.example.firewatch.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.dtos.SignUpInput
import com.example.firewatch.presentation.views.stages.RegisterSignUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    fun registerUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authService.signUp(SignUpInput(
                nif = RegisterSignUserData.nif.value!!,
                email = RegisterSignUserData.email.value!!,
                userName = RegisterSignUserData.userName.value!!,
                password = RegisterSignUserData.password.value!!,
                firstName = RegisterSignUserData.firstName.value!!,
                lastName = RegisterSignUserData.lastName.value!!,
                phoneCode = "+351",
                phoneNumber = RegisterSignUserData.phoneNumber.value!!,
                street = RegisterSignUserData.street.value!!,
                streetNumber = RegisterSignUserData.streetNumber.value!!,
                zipCode = RegisterSignUserData.zipCode.value!!,
                city = RegisterSignUserData.city.value!!,
                avatarFile = RegisterSignUserData.avatarFile.value!!,
            )
            )

            if (response.isFailure) {
                println("Failed: ${response.exceptionOrNull()?.message}")
            }

            println("Welcome!")
        }
    }
}
