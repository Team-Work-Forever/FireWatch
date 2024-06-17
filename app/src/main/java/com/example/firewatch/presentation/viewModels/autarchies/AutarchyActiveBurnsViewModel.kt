package com.example.firewatch.presentation.viewModels.autarchies

import androidx.lifecycle.ViewModel
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.Autarchy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutarchyActiveBurnsViewModel @Inject constructor(
    authService: AuthService
) : ViewModel(){
    val authUser: Autarchy? = authService.getIdentity<Autarchy>().getOrNull()
}