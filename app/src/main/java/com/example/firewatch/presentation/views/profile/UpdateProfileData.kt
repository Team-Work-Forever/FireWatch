package com.example.firewatch.presentation.views.profile

import androidx.lifecycle.MutableLiveData
import com.example.firewatch.domain.entities.User
import java.io.File

object UpdateProfileData {
    var authUser = MutableLiveData<User>(null)

    val nif = MutableLiveData<String>(authUser.value?.userName)
    val email = MutableLiveData<String>(authUser.value?.email)
    val userName = MutableLiveData<String>(authUser.value?.userName)
    val phoneCode = MutableLiveData<String>(authUser.value?.phone?.countryCode)
    val phoneNumber = MutableLiveData<String>(authUser.value?.phone?.number)
    val street = MutableLiveData<String>(authUser.value?.address?.street)
    val streetNumber = MutableLiveData<String>(authUser.value?.address?.number.toString())
    val zipCode = MutableLiveData<String>(authUser.value?.address?.zipCode)
    val city = MutableLiveData<String>(authUser.value?.address?.city)
    val avatarFile = MutableLiveData<File>()
}