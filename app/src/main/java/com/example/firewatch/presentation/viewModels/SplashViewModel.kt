package com.example.firewatch.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.presentation.views.LoginActivity
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.shared.helpers.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService
) : ViewModel() {
    val isLoading = MutableStateFlow(false)
    private var isAuth = false

    fun loadStaticValues() {
        viewModelScope.launch(Dispatchers.Main) {
            val result =  authService.checkAuth()

            isLoading.value = true
            isAuth = result.isSuccess
        }
    }

    fun loadView() {
        if (isAuth) {
            Router(authService).routeHome(context)
            return
        }

        LoginActivity.new(context)
    }
}