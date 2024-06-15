package com.example.firewatch.shared.validators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firewatch.shared.extensions.getProblem

typealias Predicate<TValue, TParam> = (value: TParam) -> Result<TValue>

class LiveDataValidator<TValueObject, TValue>(val liveData: LiveData<TValue>) {
    private lateinit var validationFun: Predicate<TValueObject, TValue>
    var error = MutableLiveData<String>()

    init {
        liveData.observeForever { _ ->
            isValid()
        }
    }

    fun getValue(): TValue = liveData.value!!

    fun isValid(): Boolean {
        if (!::validationFun.isInitialized || liveData.value == null) {
            return false
        }

        val data = validationFun(liveData.value!!)

        if (data.isFailure) {
            emitErrorMessage(data.getProblem())
            return false
        }

        clearError()
        return true
    }

    private fun emitErrorMessage(messageRes: String) {
        error.value = messageRes
    }

    fun addRule(predicate: Predicate<TValueObject, TValue>) {
        validationFun = predicate
    }

    private fun clearError() {
        error.value = ""
    }
}
