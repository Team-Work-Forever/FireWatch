package com.example.firewatch.shared.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.firewatch.domain.valueObjects.Password
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.validators.LiveDataValidator

fun LiveDataValidator<Password, String>.addPasswordCheck(liveData: LiveData<String>) {
    addRule { password ->
        val list = listOf(
            Password.create(password),
            Password.checkPassword(password, liveData.value)
        )

        list.forEach {
            if (it.isFailure) {
                return@addRule Result.failure(it.getError())
            }
        }

        return@addRule Result.success(list.first().getOrThrow())
    }
}

fun LiveDataValidator<Boolean, Boolean>.addCheckVerification() {
    addRule { value ->
        if (value) {
            return@addRule Result.success(value)
        }

        Result.failure(DomainException("value failed"))
    }
}


fun MediatorLiveData<Boolean>.canDo(): Boolean {
    return value ?: false
}

fun MediatorLiveData<Boolean>.cannotDo(): Boolean {
    return !canDo()
}

fun MediatorLiveData<Boolean>.addValidator(validator: LiveDataValidator<*, *>) {
    addSource(validator.liveData) { value = validator.isValid() }
}

fun MediatorLiveData<Boolean>.addValidators(validators: List<LiveDataValidator<*, *>>) {
    validators.forEach { validator ->
        addSource(validator.liveData) { value = validators.all { it.isValid() } }
    }
}
