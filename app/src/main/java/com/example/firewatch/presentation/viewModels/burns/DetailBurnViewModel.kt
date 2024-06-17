package com.example.firewatch.presentation.viewModels.burns

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailBurnViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val burnRepository: BurnRepository
) : ViewModel() {
    val detailBurn: MutableLiveData<Burn> = MutableLiveData(null)

    fun terminate(id: String) {
         viewModelScope.launch(Dispatchers.Main) {
             val stateResult = burnRepository.terminate(id)

             if (stateResult.isFailure) {
                Toast.makeText(context, stateResult.getProblem(), Toast.LENGTH_LONG).show()
                return@launch
             }

             Toast.makeText(context, stateResult.getOrThrow().state, Toast.LENGTH_LONG).show()
        }
    }

    fun getDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = burnRepository.get(id)

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                }

                detailBurn.postValue(response.getOrThrow())
            }
        }
    }

}