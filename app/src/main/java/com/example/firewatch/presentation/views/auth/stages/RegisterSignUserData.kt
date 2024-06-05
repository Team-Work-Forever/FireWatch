package com.example.firewatch.presentation.views.auth.stages

import androidx.lifecycle.MutableLiveData
import java.io.File

object RegisterSignUserData {
    val nif = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val phoneCode = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val street = MutableLiveData<String>()
    val streetNumber = MutableLiveData<String>()
    val zipCode = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val avatarFile = MutableLiveData<File>()
}