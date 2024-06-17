package com.example.firewatch.presentation.viewModels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleBurnsViewModel @Inject constructor(
    private val burnRepository: BurnRepository
) : ViewModel() {
    val burns: MutableLiveData<List<Burn>> = MutableLiveData(emptyList())
    val searchField = MutableLiveData<String>()

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