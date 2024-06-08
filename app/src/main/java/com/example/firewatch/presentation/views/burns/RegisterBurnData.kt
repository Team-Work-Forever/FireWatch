package com.example.firewatch.presentation.views.burns

import androidx.lifecycle.MutableLiveData
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnType
import java.math.BigDecimal
import java.time.LocalDateTime

object RegisterBurnData {
    val id = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val type = MutableLiveData<BurnType>()
    val needsAidTeam = MutableLiveData<Boolean>(false)
    val reason = MutableLiveData<BurnReason>()
    val initDate = MutableLiveData<LocalDateTime>()
    val lat = MutableLiveData<BigDecimal>()
    val lon = MutableLiveData<BigDecimal>()
}