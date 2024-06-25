package com.example.firewatch.presentation.views.views.icfn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.FragmentICFNAutarchiesBurnsBinding
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.presentation.adapters.burnCardItem.BurnCardItemAdapter
import com.example.firewatch.presentation.adapters.cardItem.CardItemDecoration
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.components.datePicker.DatePick
import com.example.firewatch.presentation.components.horizontalLine.OnSelectedItemCallBack
import com.example.firewatch.presentation.viewModels.icfn.ICFNAutarchiesBurnsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import java.math.BigDecimal
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
        val adapter = BurnCardItemAdapter()

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        adapter.setBurns(listOf(
            Burn.create(
                "",
                "",
                Coordinates.new(BigDecimal("1"), BigDecimal("1")),
                false,
                BurnReason.SANITARY_BURN,
                BurnType.BURN,
                Address.empty(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "",
                BurnState.ACTIVE
            )
        ))

        val todayDate = LocalDateTime.now()
        val initDatePicker = binding.initDatePicker
        val endDatePicker = binding.endDatePicker

        addDateToPicker(initDatePicker, viewModel.initDate, todayDate)
        addDateToPicker(endDatePicker, viewModel.endDate, todayDate)

        val horizontalLine = binding.horizontalLine
        horizontalLine.onSelectedItem(object : OnSelectedItemCallBack {
            override fun onSelectedItem(position: Int) {
                println(position)
            }
        })

        return binding.root
    }

    private fun addDateToPicker(datePicker: DatePick, date: MutableLiveData<LocalDateTime>, value: LocalDateTime) {
        datePicker.setValue(value.year, value.monthValue, value.dayOfMonth)
        date.postValue(value)
    }
}