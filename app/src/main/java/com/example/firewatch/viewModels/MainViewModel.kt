package com.example.firewatch.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.Coordinates
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainViewModel(
    private val burnRepository: BurnRepository,
    private val appContext: Context
) : ViewModel() {
    fun login() {
        viewModelScope.launch {
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