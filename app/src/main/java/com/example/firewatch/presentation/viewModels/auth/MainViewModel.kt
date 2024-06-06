package com.example.firewatch.presentation.viewModels.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext() val context: Context,
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
            val emailValue = email.value
            val passwordValue = password.value

            if (emailValue == null || passwordValue == null) {
                return@launch
            }

            val loginResult = authService.login(
                email.value!!,
                password.value!!
            )

           withContext(Dispatchers.Main) {
                if (loginResult.isFailure) {
                    Toast.makeText(context, loginResult.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Benger", Toast.LENGTH_LONG).show()
                }
           }
        }
    }
}