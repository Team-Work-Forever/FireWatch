package com.example.firewatch.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.services.http.contracts.Pagination
import kotlinx.coroutines.launch

class MainViewModel(
    private val autarchyRepository: AutarchyRepository,
    private val appContext: Context
):ViewModel() {
    fun login() {
        viewModelScope.launch {

        }
    }
}