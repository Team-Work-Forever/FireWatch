package com.example.firewatch.presentation.viewModels.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.types.EmailAuthentication
import com.example.firewatch.domain.valueObjects.Email
import com.example.firewatch.domain.valueObjects.Password
import com.example.firewatch.services.connectivity.ConnectivityService
import com.example.firewatch.shared.extensions.addValidator
import com.example.firewatch.shared.extensions.addValidators
import com.example.firewatch.shared.extensions.canDo
import com.example.firewatch.shared.extensions.cannotDo
import com.example.firewatch.shared.helpers.Router
import com.example.firewatch.shared.helpers.SwiperViews
import com.example.firewatch.shared.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext() val context: Context,
    private val authService: AuthService,
    private val connectivityService: ConnectivityService
) : ViewModel() {
    val email = MutableLiveData("")
    val emailValidator = LiveDataValidator<Email, String>(email).apply {
        addRule { Email.create(it)  }
    }

    val password = MutableLiveData("")
    val passwordValidator = LiveDataValidator<Password, String>(password).apply {
        addRule { Password.create(it)  }
    }

    val canLoginValidator = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(emailValidator, passwordValidator))
    }

    fun login() {
        viewModelScope.launch(Dispatchers.Main) {
            if (canLoginValidator.cannotDo()) {
                Toast.makeText(context, "Is not possible to do login operation", Toast.LENGTH_LONG).show()
                return@launch
            }

            val loginResult = authService.authentication(EmailAuthentication(
                emailValidator.getValue(),
                passwordValidator.getValue()
            ))

           withContext(Dispatchers.Main) {
                if (loginResult.isFailure) {
                    return@withContext Toast.makeText(context, loginResult.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
                }

                Router(
                    authService,
                    connectivityService
                ).routeHome(context)
           }
        }
    }
}