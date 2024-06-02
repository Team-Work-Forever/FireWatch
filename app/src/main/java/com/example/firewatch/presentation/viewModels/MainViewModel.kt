package com.example.firewatch.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    private val _email = MutableLiveData<String>()

    var email: MutableLiveData<String>
        get() = _email
        set(value) {
            _email.value = value.value
        }

    private val _password = MutableLiveData<String>()
    var password: MutableLiveData<String>
        get() = _password
        set(value) {
            _password.value = value.value
        }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResult = authService.login(
                email.value!!,
                password.value!!
            )

            if (loginResult.isFailure) {
                println(loginResult.exceptionOrNull()?.message)
            } else {
                println("Welcome Master!")
            }
        }
    }
}