package com.example.firewatch.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

fun <VM: ViewModel> ViewModelProvider.Companion.get(owner: ViewModelStoreOwner, viewModel: VM): VM {
    return create(owner, viewModelFactory { viewModel })[viewModel::class]
}

fun <VM: ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
    return object  : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    }
}

