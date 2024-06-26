package com.example.firewatch.presentation.viewModels.icfn

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.dtos.autarchy.AutarchyCreateInput
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.CommonObject
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Email
import com.example.firewatch.domain.valueObjects.NIF
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.extensions.addValidator
import com.example.firewatch.shared.extensions.addValidators
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
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class RegisterAutarchyViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val autarchyRepository: AutarchyRepository
) : ViewModel() {
    val name = MutableLiveData("")
    val nameValidator = LiveDataValidator<CommonObject, String>(name).apply {
        addRule { CommonObject.create(it, "name") }
    }

    val nif = MutableLiveData("")
    val nifValidator = LiveDataValidator<NIF, String>(nif).apply {
        addRule { NIF.create(it) }
    }

    val email = MutableLiveData("")
    val emailValidator = LiveDataValidator<Email, String>(email).apply {
        addRule { Email.create(it) }
    }

    val phone = MutableLiveData("")
    val phoneValidator = LiveDataValidator<CommonObject, String>(phone).apply {
        addRule { CommonObject.create(it, "phone") }
    }

    val street = MutableLiveData("")
    val streetValidator = LiveDataValidator<CommonObject, String>(street).apply {
        addRule { CommonObject.create(it, "street") }
    }

    val streetNumber = MutableLiveData("")
    val streetNumberValidator = LiveDataValidator<CommonObject, String>(streetNumber).apply {
        addRule { CommonObject.create(it, "street number") }
    }

    val zipCode = MutableLiveData("")
    val zipCodeValidator = LiveDataValidator<CommonObject, String>(zipCode).apply {
        addRule { CommonObject.create(it, "zip code") }
    }

    val city = MutableLiveData("")
    val cityValidator = LiveDataValidator<CommonObject, String>(city).apply {
        addRule { CommonObject.create(it, "city") }
    }

    val avatarFile = MutableLiveData<File>()
    val avatarValidator = LiveDataValidator<CommonObject, File>(avatarFile).apply {
        addRule { CommonObject.create(it.path, "avatar")  }
    }

    val coordinates = MutableLiveData(Coordinates.new(BigDecimal.valueOf(0), BigDecimal.valueOf(0)))
    private val coordinateValidator = LiveDataValidator<Coordinates, Coordinates>(coordinates).apply {
        addRule { (Coordinates.create(it.lat, it.lon)) }
    }

    val canStageOne = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            nameValidator,
            emailValidator,
            nifValidator,
            phoneValidator,
            avatarValidator,
        ))
    }

    val canStageTwo = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            streetValidator,
            streetNumberValidator,
            zipCodeValidator,
            cityValidator
        ))
    }

    val canStageThree = MediatorLiveData<Boolean>().apply {
        addValidator(coordinateValidator)
    }

    fun create(): Deferred<Result<Boolean>> {
        return viewModelScope.async(Dispatchers.IO) {
            if (canStageOne.cannotDo() || canStageTwo.cannotDo() || canStageThree.cannotDo()) {
                return@async Result.failure(Exception("please provide valid data to register an autarchy"))
            }

            val phone = Phone.create("+351", phone.value!!)

            if (phone.isFailure) {
                return@async Result.failure(phone.getError())
            }

            val address = Address.create(street.value!!, 10, zipCode.value!!, city.value!!)

            if (address.isFailure) {
                return@async Result.failure(address.getError())
            }

            val response = autarchyRepository.createAutarchy(
                AutarchyCreateInput(
                    nif.value!!,
                    email.value!!,
                    name.value!!,
                    Coordinates.new(
                        coordinateValidator.getValue().lat,
                        coordinateValidator.getValue().lon
                    ),
                    phone.getOrThrow(),
                    address.getOrThrow(),
                    avatarFile.value!!
                ),
            )

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                    return@withContext Result.failure(Exception(response.getProblem()))
                }

                return@withContext Result.success(true)
            }
        }
    }
}