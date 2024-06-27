package com.example.firewatch.presentation.views.views.icfn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import com.example.firewatch.shared.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDateTime

@AndroidEntryPoint
@WithFragmentBindings
class ICFNAutarchiesBurns : HomeView<ICFNAutarchiesBurnsViewModel>(ICFNAutarchiesBurnsViewModel::class.java) {
    private lateinit var binding: FragmentICFNAutarchiesBurnsBinding

    override fun onPageRefresh() {
        viewModel.fetch()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentICFNAutarchiesBurnsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetch()
            }
        }

        val recyclerView: RecyclerView = binding.autarchiesBurnsList
        val adapter = BurnCardItemAdapter()

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        viewModel.burns.observe(viewLifecycleOwner, Observer {
            adapter.setBurns(it)
        })

        viewModel.searchField.observe(viewLifecycleOwner, Observer {
            viewModel.getBurns(
                search = it,
            )
        })

        val initDatePicker = binding.initDatePicker
        val endDatePicker = binding.endDatePicker

        addDateToPicker(initDatePicker, viewModel.initDate, LocalDateTime.now().minusDays(6))

        initDatePicker.setOnDatePickClick { _, year, month, dayOfMonth ->
            val date = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
            addDateToPicker(initDatePicker, viewModel.initDate, date)

            viewModel.fetch()
        }

        addDateToPicker(endDatePicker, viewModel.endDate, LocalDateTime.now().plusDays(6))

        endDatePicker.setOnDatePickClick { _, year, month, dayOfMonth ->
            val date = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
            addDateToPicker(endDatePicker, viewModel.endDate, date)

            viewModel.fetch()
        }

        val horizontalLine = binding.horizontalLine
        horizontalLine.selectItemOnIndex(0)
        viewModel.burnState.value = BurnState.ACTIVE.state

        horizontalLine.onSelectedItem(object : OnSelectedItemCallBack {
            override fun onSelectedItem(position: Int) {
                when (position) {
                    0 -> viewModel.burnState.value = BurnState.ACTIVE.state
                    1 -> viewModel.burnState.value = BurnState.SCHEDULED.state
                    2 -> viewModel.burnState.value = BurnState.COMPLETED.state
                }

                viewModel.fetch()
            }
        })

        return binding.root
    }

    private fun addDateToPicker(datePicker: DatePick, date: MutableLiveData<LocalDateTime>, value: LocalDateTime) {
        datePicker.setValue(value.year, value.monthValue, value.dayOfMonth)
        date.value = value
    }
}