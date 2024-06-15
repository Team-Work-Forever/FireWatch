package com.example.firewatch.presentation.viewModels.burns

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditBurnDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val burnRepository: BurnRepository
) : ViewModel() {
    fun removeBurn(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = burnRepository.delete(id)

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    return@withContext Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                }

                Toast.makeText(context, response.getOrThrow(), Toast.LENGTH_LONG).show()
            }
        }
    }
}