package com.example.firewatch.presentation.viewModels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val burnRepository: BurnRepository
) : ViewModel() {
    val authUser: User? = authService.getIdentity<User>().getOrNull()
    val burns: MutableLiveData<List<Burn>> = MutableLiveData(emptyList())

    fun getLastBurns() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = burnRepository.getAll(
                state = BurnState.COMPLETED.state,
                sort = "desc"
            )

            if (response.isFailure) {
                return@launch
            }

            burns.postValue(response.getOrThrow())
        }
    }
}