package com.example.firewatch.presentation.viewModels.icfn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.repositories.dtos.autarchy.AutarchyUpdateInput
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.extensions.getProblem
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

    val name = MutableLiveData<String>()
    val nif = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phoneCode = MutableLiveData<String>("+351")
    val phoneNumber = MutableLiveData<String>()
    val street = MutableLiveData<String>()
    val streetNumber = MutableLiveData<String>()
    val zipCode = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val avatarFile = MutableLiveData<File>()

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

                println(result.getOrThrow().avatar)
                println(autarchy.value?.avatar)

                return@withContext result.isSuccess
            }
        }
    }

    fun updateById(id: String): Deferred<Result<String>> {
        return viewModelScope.async {
            var phone: Phone? = null
            var address: Address? = null

            if (phoneCode.value != null && phoneNumber.value != null) {
                phone = Phone.create(phoneCode.value!!, phoneNumber.value!!)
            }

            if (street.value != null && streetNumber.value != null && zipCode.value != null && city.value != null) {
                address = Address.create(
                    street.value!!,
                    streetNumber.value!!.toInt(),
                    zipCode.value!!,
                    city.value!!
                )
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