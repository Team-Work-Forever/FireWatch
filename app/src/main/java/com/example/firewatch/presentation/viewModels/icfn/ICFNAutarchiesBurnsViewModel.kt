package com.example.firewatch.presentation.viewModels.icfn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.BaseUser
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.InitDate
import com.example.firewatch.domain.valueObjects.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ICFNAutarchiesBurnsViewModel @Inject constructor(
    private val burnRepository: BurnRepository,
    private val autarchyRepository: AutarchyRepository,
    private val authService: AuthService
) : ViewModel() {
    val authUser: BaseUser? = authService.getIdentity<BaseUser>().getOrNull()

    val burns: MutableLiveData<List<Burn>> = MutableLiveData(emptyList())
    val searchField = MutableLiveData<String>()

    val initDate = MutableLiveData<LocalDateTime>()
    val endDate = MutableLiveData<LocalDateTime>()
    val burnState = MutableLiveData<String>()

    fun fetch() {
        authUser?.let {
            if (it.userType == UserType.AUTARCHY) {
                return getBurnsOfAutarchy(
                    id = it.id
                )
            }
        }

        getBurns(
            search = searchField.value
        )
    }

    private fun getBurnsOfAutarchy(
        id: String,
        search: String? = null,
        state: BurnState? = null,
        startDate: LocalDateTime? = null,
        lastDate: LocalDateTime? = null,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = autarchyRepository.getAllBurns(
                id = id,
                search = search ?: searchField.value,
                state = state?.state ?: burnState.value,
                startDate = startDate ?: initDate.value,
                endDate = lastDate ?: endDate.value
            )

            if (response.isFailure) {
                return@launch
            }

            burns.postValue(response.getOrThrow())
        }
    }

    fun getBurns(
        search: String? = null,
        state: BurnState? = null,
        startDate: LocalDateTime? = null,
        lastDate: LocalDateTime? = null,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = burnRepository.getAll(
                search = search ?: searchField.value,
                state = state?.state ?: burnState.value,
                startDate = startDate ?: initDate.value,
                endDate = lastDate ?: endDate.value
            )

            if (response.isFailure) {
                return@launch
            }

            burns.postValue(response.getOrThrow())
        }
    }
}