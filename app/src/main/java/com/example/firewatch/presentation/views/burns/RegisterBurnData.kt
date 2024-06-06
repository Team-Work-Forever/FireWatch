package com.example.firewatch.presentation.views.burns

import androidx.lifecycle.MutableLiveData

object RegisterBurnData {
    val name = MutableLiveData<String>()
    val type = MutableLiveData<String>()
    val needsAidTeam = MutableLiveData<Boolean>()
    val reason = MutableLiveData<String>()
    val initDate = MutableLiveData<String>()
    val lat = MutableLiveData<String>()
    val lon = MutableLiveData<String>()
}