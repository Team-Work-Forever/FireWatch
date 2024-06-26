package com.example.firewatch.presentation.viewModels.burns

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.dtos.burn.BurnCreateInput
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.CommonObject
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.InitDate
import com.example.firewatch.shared.extensions.addValidator
import com.example.firewatch.shared.extensions.addValidators
import com.example.firewatch.shared.extensions.cannotDo
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
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RegisterBurnViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val burnRepository: BurnRepository
) : ViewModel() {
    val name = MutableLiveData("")
    val nameValidator = LiveDataValidator<CommonObject, String>(name).apply {
        addRule { CommonObject.create(it, "name")  }
    }

    val type = MutableLiveData<BurnType>()
    val file = MutableLiveData<File>()
    val needsAidTeam = MutableLiveData(false)
    val reason = MutableLiveData<BurnReason>()
    val initDate = MutableLiveData<LocalDateTime>()
    val initDateValidator = LiveDataValidator<InitDate, LocalDateTime>(initDate).apply {
        addRule { InitDate.create(it)  }
    }

    val coordinates = MutableLiveData(Coordinates.new(BigDecimal.valueOf(0), BigDecimal.valueOf(0)))
    private val coordinateValidator = LiveDataValidator<Coordinates, Coordinates>(coordinates).apply {
        addRule { (Coordinates.create(it.lat, it.lon)) }
    }

    val canStageOne = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            nameValidator,
            initDateValidator,
        ))
    }

    val canStageTwo = MediatorLiveData<Boolean>().apply {
        addValidator(coordinateValidator)
    }

    fun create(): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            if (canStageOne.cannotDo() || canStageTwo.cannotDo()) {
                return@async false
            }

            val coordinates = coordinateValidator.getValue()

            val response = burnRepository.create(
                BurnCreateInput(
                    name.value!!,
                    coordinates,
                    reason.value!!,
                    type.value!!,
                    needsAidTeam.value!!,
                    "Quero ver algo queimar!",
                    initDate.value!!,
                    file.value
                )
            )

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                    return@withContext false
                }

                val registerBurnResult = response.getOrThrow()
                return@withContext checkPossibility(registerBurnResult.state)
            }
        }
    }

    private fun checkPossibility(state: BurnState): Boolean {
        return state == BurnState.ACTIVE || state == BurnState.SCHEDULED
    }
}