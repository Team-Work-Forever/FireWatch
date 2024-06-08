package com.example.firewatch.presentation.viewModels.burns

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.dtos.burn.BurnCreateInput
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RegisterBurnViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val burnRepository: BurnRepository
) : ViewModel() {
    val name = MutableLiveData<String>()
    val type = MutableLiveData<BurnType>()
    val needsAidTeam = MutableLiveData(false)
    val reason = MutableLiveData<BurnReason>()
    val initDate = MutableLiveData<LocalDateTime>()
    val lat = MutableLiveData<BigDecimal>()
    val lon = MutableLiveData<BigDecimal>()

    fun create(): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            val response = burnRepository.create(
                BurnCreateInput(
                    name.value!!,
                    Coordinates.create(lat.value!!,lon.value!!),
                    reason.value!!,
                    type.value!!,
                    needsAidTeam.value!!,
                    "Quero ver algo queimar!",
                    initDate.value!!,
                )
            )

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                    return@withContext false
                }

                val registerBurnResult = response.getOrThrow()
                Toast.makeText(context, registerBurnResult.id, Toast.LENGTH_LONG).show()
                return@withContext registerBurnResult.state == BurnState.ACTIVE
            }
        }
    }
}