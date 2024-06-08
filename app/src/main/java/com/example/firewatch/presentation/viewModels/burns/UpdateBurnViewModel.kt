package com.example.firewatch.presentation.viewModels.burns

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.dtos.burn.BurnUpdateInput
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
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
class UpdateBurnViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val burnRepository: BurnRepository
) : ViewModel() {

    fun updateBurn(id: String): Deferred<Boolean> {
        return viewModelScope.async {
            var coordinates: Coordinates? = null

            if (RegisterBurnData.lat.value != null && RegisterBurnData.lon.value != null) {
                coordinates = Coordinates.create(RegisterBurnData.lat.value!!, RegisterBurnData.lon.value!!)
            }

            val response = burnRepository.update(BurnUpdateInput(
                id,
                RegisterBurnData.name.value,
                coordinates,
                RegisterBurnData.reason.value,
                RegisterBurnData.type.value,
                RegisterBurnData.needsAidTeam.value,
                "Quero mesmo arder algo",
                RegisterBurnData.initDate.value,
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