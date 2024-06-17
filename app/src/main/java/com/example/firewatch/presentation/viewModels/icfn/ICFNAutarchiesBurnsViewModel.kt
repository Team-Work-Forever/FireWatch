package com.example.firewatch.presentation.viewModels.icfn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ICFNAutarchiesBurnsViewModel @Inject constructor(

) : ViewModel() {
    val searchField = MutableLiveData<String>()

    val initDate = MutableLiveData<LocalDateTime>()
    val endDate = MutableLiveData<LocalDateTime>()


}