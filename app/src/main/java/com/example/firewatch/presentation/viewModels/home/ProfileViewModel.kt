package com.example.firewatch.presentation.viewModels.home

import androidx.lifecycle.ViewModel
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    val authUser: User? = authService.getIdentity<User>().getOrNull()
}