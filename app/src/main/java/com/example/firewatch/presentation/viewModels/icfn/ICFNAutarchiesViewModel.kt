package com.example.firewatch.presentation.viewModels.icfn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ICFNAutarchiesViewModel @Inject constructor(
    private val autarchyRepository: AutarchyRepository
) : ViewModel() {
    val autarchies: MutableLiveData<List<Autarchy>> = MutableLiveData(emptyList())
    val searchField = MutableLiveData<String>()

    fun getAllAutarchies(
        search: String? = null
    ) {
         viewModelScope.launch(Dispatchers.IO) {
            val response = autarchyRepository.getAll(
                search = search,
            )

            if (response.isFailure) {
                return@launch
            }

            autarchies.postValue(response.getOrThrow())
        }
    }
}