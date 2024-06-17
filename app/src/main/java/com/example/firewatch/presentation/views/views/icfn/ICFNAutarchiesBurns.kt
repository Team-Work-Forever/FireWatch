package com.example.firewatch.presentation.views.views.icfn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentICFNAutarchiesBurnsBinding
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.components.datePicker.DatePick
import com.example.firewatch.presentation.viewModels.icfn.ICFNAutarchiesBurnsViewModel
import com.example.firewatch.shared.helpers.DateHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import java.time.LocalDateTime

@AndroidEntryPoint
@WithFragmentBindings
class ICFNAutarchiesBurns : HomeView<ICFNAutarchiesBurnsViewModel>(ICFNAutarchiesBurnsViewModel::class.java) {
    private lateinit var binding: FragmentICFNAutarchiesBurnsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentICFNAutarchiesBurnsBinding.inflate(layoutInflater)

         val recyclerView: RecyclerView = binding.autarchiesBurnsList
//        val adapter = CardItemAdapter(
//            requireActivity(),
//            bottomClick = { burn ->
//                when (burn.state) {
//                    BurnState.ACTIVE -> {
//                        viewModel.terminateBurn(burn.id)
//                    }
//                    BurnState.SCHEDULED -> TODO()
//                    else -> TODO()
//                }
//            },
//            hasBottom = true
//        )
//
//        recyclerView.adapter = adapter
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.addItemDecoration(CardItemDecoration())

        val todayDate = LocalDateTime.now()
        val initDatePicker = binding.initDatePicker
        val endDatePicker = binding.endDatePicker

        addDateToPicker(initDatePicker, viewModel.initDate, todayDate)
        addDateToPicker(endDatePicker, viewModel.endDate, todayDate)

        return binding.root
    }


    private fun addDateToPicker(datePicker: DatePick, date: MutableLiveData<LocalDateTime>, value: LocalDateTime) {
        datePicker.setValue(value.year, value.monthValue, value.dayOfMonth)
        date.postValue(value)
    }
}