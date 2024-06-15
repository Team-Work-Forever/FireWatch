package com.example.firewatch.presentation.viewModels.burns

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.dtos.burn.BurnUpdateInput
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.CommonObject
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Email
import com.example.firewatch.domain.valueObjects.InitDate
import com.example.firewatch.shared.extensions.addValidators
import com.example.firewatch.shared.extensions.cannotDo
import com.example.firewatch.shared.extensions.getProblem
import com.example.firewatch.shared.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class UpdateBurnViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val burnRepository: BurnRepository
) : ViewModel() {
    companion object {
        var id: String = ""
    }

    val name = MutableLiveData("")
    val nameValidator = LiveDataValidator<CommonObject, String>(name).apply {
        addRule { CommonObject.create(it, "name")  }
    }

    val type = MutableLiveData<BurnType>()
    val needsAidTeam = MutableLiveData(false)
    val reason = MutableLiveData<BurnReason>()
    val initDate = MutableLiveData<LocalDateTime>()
    val initDateValidator = LiveDataValidator<InitDate, LocalDateTime>(initDate).apply {
        addRule { InitDate.create(it)  }
    }

    val lat = MutableLiveData<BigDecimal>()
    val lon = MutableLiveData<BigDecimal>()

    val canStageOne = MediatorLiveData<Boolean>().apply {
        addValidators(listOf(
            nameValidator,
            initDateValidator
        ))
    }

    fun getBurnById(): Deferred<Result<Burn>> {
        return viewModelScope.async {
            burnRepository.get(id)
        }
    }

    fun updateBurn(id: String): Deferred<Boolean> {
        return viewModelScope.async {
            var coordinates: Coordinates? = null

            if (canStageOne.cannotDo()) {
                return@async false
            }

            if (id.isEmpty()) {
                return@async false
            }

            if (lat.value != null && lon.value != null) {
                coordinates = Coordinates.create(lat.value!!, lon.value!!)
            }

            val response = burnRepository.update(BurnUpdateInput(
                id,
                name.value,
                coordinates,
                reason.value,
                type.value,
                needsAidTeam.value,
                "Quero mesmo arder algo",
                initDate.value,
            ))

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                    return@withContext false
                }

                return@withContext true
            }
        }
    }

}