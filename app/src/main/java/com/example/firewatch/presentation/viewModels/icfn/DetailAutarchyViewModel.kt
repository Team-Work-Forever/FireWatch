package com.example.firewatch.presentation.viewModels.icfn

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.repositories.interfaces.AutarchyRepository
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailAutarchyViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService,
    private val autarchyRepository: AutarchyRepository
) : ViewModel() {
    val autarchyDetail: MutableLiveData<Autarchy> = MutableLiveData(null)

    fun removeAutarchyId(id: String): Deferred<Result<Boolean>> {
        return viewModelScope.async {
            val response = autarchyRepository.delete(id)

            return@async withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()

                     Result.failure<Exception>(Exception(response.getProblem()))
                }

                Toast.makeText(context, response.getOrThrow(), Toast.LENGTH_LONG).show()
                Result.success(response.isSuccess)
            }
        }
    }

    fun getAutarchyById(id: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == null) return@launch

            val response = autarchyRepository.get(id)

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                }

                autarchyDetail.postValue(response.getOrThrow())
            }
        }
    }
}