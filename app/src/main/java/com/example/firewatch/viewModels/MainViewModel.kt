package com.example.firewatch.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.dtos.ProfileUpdateInput
import com.example.firewatch.domain.repositories.ProfileRepositoryImpl
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
    private val profileRepository: ProfileRepository,
    private val appContext: Context
):ViewModel() {
    fun login() {
        viewModelScope.launch {

        }
    }
}