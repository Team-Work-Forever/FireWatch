package com.example.firewatch.presentation.viewModels.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.Coordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val burnRepository: BurnRepository
) : ViewModel() {
    fun check(coordinates: Coordinates): Deferred<Result<Boolean>> {
        return viewModelScope.async {
            return@async burnRepository.getAvailabitity(coordinates)
        }
    }
}