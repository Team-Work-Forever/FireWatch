package com.example.firewatch.presentation.viewModels.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleBurnsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val burnRepository: BurnRepository
) : ViewModel() {
    val burns: MutableLiveData<List<Burn>> = MutableLiveData(emptyList())
    val searchField = MutableLiveData<String>()

    fun fetch() {
       getBurns(
            state = BurnState.SCHEDULED
        )
    }

    fun schedualeBurn(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = burnRepository.start(id)

            if (result.isFailure) {
                Toast.makeText(context, result.getProblem(), Toast.LENGTH_LONG).show()
            }

            fetch()
        }
    }

    fun getBurns(
        search: String? = null,
        state: BurnState? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = burnRepository.getAll(
                search = search,
                state = state?.state
            )

            if (response.isFailure) {
                return@launch
            }

            burns.postValue(response.getOrThrow())
        }
    }
}