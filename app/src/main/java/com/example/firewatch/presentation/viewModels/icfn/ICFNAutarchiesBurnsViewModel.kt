package com.example.firewatch.presentation.viewModels.icfn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.InitDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ICFNAutarchiesBurnsViewModel @Inject constructor(
    private val burnRepository: BurnRepository
) : ViewModel() {
    val burns: MutableLiveData<List<Burn>> = MutableLiveData(emptyList())
    val searchField = MutableLiveData<String>()

    val initDate = MutableLiveData<LocalDateTime>()
    val endDate = MutableLiveData<LocalDateTime>()
    val burnState = MutableLiveData<String>()

    fun fetch() {
        getBurns(
            search = searchField.value
        )
    }

    fun getBurns(
        search: String? = null,
        state: BurnState? = null,
        startDate: LocalDateTime? = null,
        lastDate: LocalDateTime? = null,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = burnRepository.getAll(
                search = search ?: searchField.value,
                state = state?.state ?: burnState.value,
                startDate = startDate ?: initDate.value,
                endDate = lastDate ?: endDate.value
            )

            if (response.isFailure) {
                return@launch
            }

            burns.postValue(response.getOrThrow())
        }
    }
}