package com.example.firewatch.presentation.viewModels.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.dtos.SignUpInput
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext val context: Context,
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

    fun registerUser(): Deferred<Result<String>> {
       return viewModelScope.async(Dispatchers.IO) {
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
            ))

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    return@withContext Result.failure(Exception(response.getProblem()))
                }

                return@withContext Result.success("All worked")
            }
        }
    }
}
