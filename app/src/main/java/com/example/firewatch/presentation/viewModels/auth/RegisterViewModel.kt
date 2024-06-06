package com.example.firewatch.presentation.viewModels.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.dtos.SignUpInput
import com.example.firewatch.presentation.views.auth.stages.RegisterSignUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext val context: Context,
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
            ))

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    return@withContext Toast.makeText(context, response.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
