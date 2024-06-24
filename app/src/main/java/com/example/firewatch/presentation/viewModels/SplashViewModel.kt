package com.example.firewatch.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.presentation.views.LoginActivity
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.repositories.dtos.burn.BurnRequest
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.shared.helpers.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val burnRepository: BurnRepository,
    private val authService: AuthService
) : ViewModel() {
    val isLoading = MutableStateFlow(false)

    private fun loadStaticValues() {

    }

    fun loadView(context: Context) {
       viewModelScope.launch {
           val result =  authService.checkAuth()

           if (result.isSuccess) {
               Router(authService).routeHome(context)
               return@launch
           }

           LoginActivity.new(context)
       }
    }
}