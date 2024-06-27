package com.example.firewatch.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.presentation.views.LoginActivity
import com.example.firewatch.services.store.StoreController
import com.example.firewatch.services.store.options.SliderStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SliderViewModel @Inject constructor(
    private val storeController: StoreController
): ViewModel() {
    fun goForward(context: Context) {
        storeController.set(SliderStore(true))
        LoginActivity.new(context)
    }
}