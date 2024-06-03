package com.example.firewatch.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.dtos.SignUpInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    val nif = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val phoneCode = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val street = MutableLiveData<String>()
    val streetNumber = MutableLiveData<String>()
    val zipCode = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val avatarFile = MutableLiveData<File>()

    fun registerUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authService.signUp(SignUpInput(
                nif = nif.value!!,
                email = email.value!!,
                userName = userName.value!!,
                password = password.value!!,
                firstName = firstName.value!!,
                lastName = lastName.value!!,
                phoneCode = "+351",
                phoneNumber = phoneNumber.value!!,
                street = street.value!!,
                streetNumber = streetNumber.value!!,
                zipCode = zipCode.value!!,
                city = city.value!!,
                avatarFile = avatarFile.value!!,
            )
            )

            if (response.isFailure) {
                println("Failed: ${response.exceptionOrNull()?.message}")
            }

            println("Welcome!")
        }
    }
}
