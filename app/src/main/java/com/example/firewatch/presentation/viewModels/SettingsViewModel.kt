package com.example.firewatch.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.firewatch.context.auth.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    fun logout() = authService.logout()
}