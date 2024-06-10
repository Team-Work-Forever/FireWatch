package com.example.firewatch.presentation.viewModels.icfn

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.dtos.autarchy.AutarchyCreateInput
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.extensions.getProblem
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
    val name = MutableLiveData<String>()
    val nif = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val street = MutableLiveData<String>()
    val streetNumber = MutableLiveData<String>()
    val zipCode = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val avatarFile = MutableLiveData<File>()

    fun create(): Deferred<Result<Boolean>> {
        return viewModelScope.async(Dispatchers.IO) {
            val response = autarchyRepository.createAutarchy(
                AutarchyCreateInput(
                    nif.value!!,
                    email.value!!,
                    name.value!!,
                    Coordinates.create(
                        BigDecimal("41.37965437813482"),
                        BigDecimal("-8.759883087733167")
                    ),
                    Phone.create("+351", phone.value!!),
                    Address.create(
                        street.value!!,
                        10,
                        zipCode.value!!,
                        city.value!!
                    ),
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