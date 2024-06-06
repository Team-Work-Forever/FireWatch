package com.example.firewatch.presentation.viewModels.burns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.dtos.burn.BurnCreateInput
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterBurnViewModel @Inject constructor(
    private val burnRepository: BurnRepository
) : ViewModel() {

    fun create() {
    }
}