package com.example.firewatch.presentation.viewModels.icfn

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.repositories.dtos.autarchy.AutarchyUpdateInput
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.CommonObject
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Email
import com.example.firewatch.domain.valueObjects.NIF
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.extensions.addValidators
import com.example.firewatch.shared.extensions.cannotDo
import com.example.firewatch.shared.extensions.getError
import com.example.firewatch.shared.extensions.getProblem
import com.example.firewatch.shared.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateAutarchyViewModel @Inject constructor(
    private val autarchyRepository: AutarchyRepository
) : ViewModel() {
    val autarchy = MutableLiveData<Autarchy>()

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

    val phoneCode = MutableLiveData("+351")
    val phoneNumber = MutableLiveData("")
    val phoneValidator = LiveDataValidator<CommonObject, String>(phoneNumber).apply {
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

    val canStageOne = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            nameValidator,
            phoneValidator,
            nifValidator,
            emailValidator,
            avatarValidator
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
    companion object {
        var id: String = ""
    }

    fun getAutarchyId(id: String): Deferred<Boolean> {
        return viewModelScope.async {
            val result = autarchyRepository.get(id)

            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    autarchy.value = result.getOrThrow()
                }

                return@withContext result.isSuccess
            }
        }
    }

    fun updateById(id: String): Deferred<Result<String>> {
        return viewModelScope.async {
            var phone: Phone? = null
            var address: Address? = null

            if (canStageOne.cannotDo() || canStageTwo.cannotDo()) {
                return@async Result.failure(Exception("please provide the necessary data for this operation"))
            }

            if (phoneCode.value != null && phoneNumber.value != null) {
                val phoneResult = Phone.create(phoneCode.value!!, phoneNumber.value!!)

                if (phoneResult.isFailure) {
                    return@async Result.failure(phoneResult.getError())
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
                    return@async Result.failure(addressResult.getError())
                }

                address = addressResult.getOrThrow()
            }

            val response = autarchyRepository.update(AutarchyUpdateInput(
                id,
                email.value,
                name.value,
                autarchy.value?.coordinates,
                phone,
                address,
                avatarFile.value
            ))

            return@async withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Result.failure<Exception>(Exception(response.getProblem()))
                }

                Result.success("Updated")
            }
        }
    }
}