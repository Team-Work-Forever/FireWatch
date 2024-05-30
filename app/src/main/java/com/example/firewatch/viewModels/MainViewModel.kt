package com.example.firewatch.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.Coordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val burnRepository: BurnRepository,
) : ViewModel() {
    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            val isAvailable = burnRepository.getAvailabitity(
                Coordinates.create(
                    BigDecimal("41.38445821966337"),
                    BigDecimal("-8.745004836915294")
                )
            )

            println(isAvailable)
        }
    }
}