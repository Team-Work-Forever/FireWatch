package com.example.firewatch.presentation.viewModels.burns

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.dtos.burn.BurnCreateInput
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.presentation.views.burns.RegisterBurnData
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterBurnViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val burnRepository: BurnRepository
) : ViewModel() {

    fun create(): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            val response = burnRepository.create(
                BurnCreateInput(
                    RegisterBurnData.name.value!!,
                    Coordinates.create(RegisterBurnData.lat.value!!, RegisterBurnData.lon.value!!),
                    RegisterBurnData.reason.value!!,
                    RegisterBurnData.type.value!!,
                    RegisterBurnData.needsAidTeam.value!!,
                    "Quero ver algo queimar!",
                    RegisterBurnData.initDate.value!!,
                )
            )

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                    return@withContext false
                }

                Toast.makeText(context, response.getOrThrow().id, Toast.LENGTH_LONG).show()
                return@withContext true
            }
        }
    }
}