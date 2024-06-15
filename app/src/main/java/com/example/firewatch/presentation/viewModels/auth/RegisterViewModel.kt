package com.example.firewatch.presentation.viewModels.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.context.auth.dtos.SignUpInput
import com.example.firewatch.domain.valueObjects.CommonObject
import com.example.firewatch.domain.valueObjects.Email
import com.example.firewatch.domain.valueObjects.NIF
import com.example.firewatch.domain.valueObjects.Password
import com.example.firewatch.domain.valueObjects.ZipCode
import com.example.firewatch.shared.extensions.addCheckVerification
import com.example.firewatch.shared.extensions.addPasswordCheck
import com.example.firewatch.shared.extensions.getProblem
import com.example.firewatch.shared.validators.LiveDataValidator
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
    val userName = MutableLiveData("")
    val userNameValidator = LiveDataValidator<CommonObject, String>(userName).apply {
        addRule { CommonObject.create(it, "user name")  }
    }

    val firstName = MutableLiveData("")
    val firstNameValidator = LiveDataValidator<CommonObject, String>(firstName).apply {
        addRule { CommonObject.create(it, "first name")  }
    }

    val lastName = MutableLiveData("")
    val lastNameValidator = LiveDataValidator<CommonObject, String>(lastName).apply {
        addRule { CommonObject.create(it, "last name")  }
    }

    val phoneCode = MutableLiveData("")
    val phoneNumber = MutableLiveData("")
    val phoneValidator = LiveDataValidator<CommonObject, String>(phoneNumber).apply {
        addRule { CommonObject.create(it, "phone")  }
    }

    val nif = MutableLiveData("")
    val nifValidator = LiveDataValidator<NIF, String>(nif).apply {
        addRule { NIF.create(it)  }
    }

    val email = MutableLiveData("")
    val emailValidator = LiveDataValidator<Email, String>(email).apply {
        addRule { Email.create(it)  }
    }

    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    val passwordValidator = LiveDataValidator<Password, String>(password).apply {
        addPasswordCheck(confirmPassword)
    }

    val confirmPasswordValidator = LiveDataValidator<Password, String>(confirmPassword).apply {
        addPasswordCheck(password)
    }

    val street = MutableLiveData("")
    val streetValidator = LiveDataValidator<CommonObject, String>(street).apply {
        addRule { CommonObject.create(it, "street")  }
    }

    val streetNumber = MutableLiveData("")
    val streetNumberValidator = LiveDataValidator<CommonObject, String>(streetNumber).apply {
        addRule { CommonObject.create(it, "streetNumber")  }
    }

    val zipCode = MutableLiveData("")
    val zipCodeValidator = LiveDataValidator<ZipCode, String>(zipCode).apply {
        addRule { ZipCode.create(it)  }
    }

    val city = MutableLiveData("")
    val cityValidator = LiveDataValidator<CommonObject, String>(city).apply {
        addRule { CommonObject.create(it, "city")  }
    }

    val avatarFile = MutableLiveData<File>()
    val avatarValidator = LiveDataValidator<CommonObject, File>(avatarFile).apply {
        addRule { CommonObject.create(it.path, "avatar")  }
    }

    val rgpd = MutableLiveData(false)
    val rgpdValidator = LiveDataValidator<Boolean, Boolean>(rgpd).apply {
        addCheckVerification()
    }
    fun registerUser(): Deferred<Result<String>> {
        return viewModelScope.async(Dispatchers.Main) {
            val response = authService.signUp(SignUpInput(
                nif = nifValidator.getValue(),
                email = emailValidator.getValue(),
                userName = userNameValidator.getValue(),
                password = passwordValidator.getValue(),
                firstName = firstNameValidator.getValue(),
                lastName = lastNameValidator.getValue(),
                phoneCode = "+351",
                phoneNumber = phoneNumber.value!!,
                street = streetValidator.getValue(),
                streetNumber = streetNumberValidator.getValue(),
                zipCode = zipCodeValidator.getValue(),
                city = cityValidator.getValue(),
                avatarFile = avatarValidator.getValue(),
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
