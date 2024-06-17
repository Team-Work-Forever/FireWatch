package com.example.firewatch.presentation.viewModels.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.repositories.dtos.profile.ProfileUpdateInput
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.CommonObject
import com.example.firewatch.domain.valueObjects.Email
import com.example.firewatch.domain.valueObjects.NIF
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.domain.valueObjects.ZipCode
import com.example.firewatch.shared.extensions.addValidators
import com.example.firewatch.shared.extensions.canDo
import com.example.firewatch.shared.extensions.cannotDo
import com.example.firewatch.shared.extensions.getError
import com.example.firewatch.shared.extensions.getProblem
import com.example.firewatch.shared.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val authUser: User? = authService.getIdentity<User>().getOrNull()

   val userName = MutableLiveData("")
    val userNameValidator = LiveDataValidator<CommonObject, String>(userName).apply {
        addRule { CommonObject.create(it, "user name")  }
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

    val canStageOne = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            userNameValidator,
            nifValidator,
            avatarValidator
        ))
    }

    val canStageTwo = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            emailValidator,
            streetValidator,
            streetNumberValidator,
            zipCodeValidator,
            cityValidator
        ))
    }

    fun updateProfile(): Deferred<Boolean> {
        return viewModelScope.async {
            var phone: Phone? = null
            var address: Address? = null
            var avatar: File? = null

            if (canStageOne.cannotDo() || canStageTwo.cannotDo()) {
                return@async false
            }

            if (phoneCode.value != null && phoneNumber.value != null) {
                val phoneResult = Phone.create(phoneCode.value!!, phoneNumber.value!!)

                if (phoneResult.isFailure) {
                    return@async false
                }

                phone = phoneResult.getOrThrow()
            }

            if (street.value != null && streetNumber.value != null && zipCode.value != null && city.value != null) {
                val addressResult = Address.create(
                    street.value!!,
                    streetNumber.value!!.toInt(),
                    zipCode.value!!,
                    city.value!!
                )

                if (addressResult.isFailure) {
                    return@async false
                }

                address = addressResult.getOrThrow()
            }

            if (avatarFile.value!!.path != "default") {
                avatar = avatarFile.value
            }

            val response = profileRepository.update(ProfileUpdateInput(
                email.value,
                avatar,
                userName.value,
                null,
                null,
                phone,
                address
            ))

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                    return@withContext false
                }

                return@withContext authService.fetchProfile().isSuccess
            }
        }
    }
}