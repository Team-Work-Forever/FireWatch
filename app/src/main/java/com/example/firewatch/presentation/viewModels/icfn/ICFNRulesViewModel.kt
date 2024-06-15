package com.example.firewatch.presentation.viewModels.icfn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ICFNRulesViewModel @Inject constructor(
    private val authService: AuthService,
    private val autarchyRepository: AutarchyRepository
) : ViewModel() {
    val authUser: User? = authService.getIdentity<User>().getOrNull()
    val autarchies: MutableLiveData<List<Autarchy>> = MutableLiveData(emptyList())

    fun getAllAutarchies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = autarchyRepository.getAll()

            if (response.isFailure) {
                return@launch
            }

            autarchies.postValue(response.getOrThrow())
        }
    }
}