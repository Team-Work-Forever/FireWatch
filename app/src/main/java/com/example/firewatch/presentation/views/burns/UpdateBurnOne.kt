package com.example.firewatch.presentation.views.burns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentUpdateBurnOneBinding
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.components.dropDown.DefaultDropDrownAdapter
import com.example.firewatch.presentation.components.dropDown.OnDropDownItemSelected
import com.example.firewatch.presentation.viewModels.burns.UpdateBurnViewModel
import com.example.firewatch.presentation.viewModels.burns.UpdateState
import com.example.firewatch.shared.helpers.DateHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class UpdateBurnOne : Stage<UpdateBurnViewModel>(UpdateBurnViewModel::class.java) {
    private lateinit var binding: FragmentUpdateBurnOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBurnOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        if (UpdateBurnViewModel.state == UpdateState.REPEAT) {
            header.setTitle(getString(R.string.repeat))
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            val burnResult =  viewModel.getBurnById().await()

            if (burnResult.isFailure) {
                exit()
            }

            viewModel.burn.value = burnResult.getOrThrow()
            setValueOn(binding.updateBurnName, viewModel.name, viewModel.burn.value!!.title)

            binding.datePicker.setValue(viewModel.burn.value!!.beginAt)
            viewModel.initDate.postValue(viewModel.burn.value!!.beginAt)
        }

        header.setOnBackListener() {
            exit()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        binding.datePicker.setOnDatePickClick { _, year, month, dayOfMonth ->
            binding.datePicker.setValue(year, month, dayOfMonth)
            viewModel.initDate.postValue(DateHelper.getLocalDateTime(year, month, dayOfMonth))
        }

        val typeDropDown = binding.typeDropDown
        typeDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
            override fun onItemSelected(item: String) {
                val burnType = BurnType.getFromDescription(context, item)
                 viewModel.type.postValue(burnType!!)
            }
        })

        typeDropDown.setAdapter(DefaultDropDrownAdapter(requireActivity(), BurnType.getValues(context)))

        val reasonDropDown = binding.reasonDropDown
        reasonDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
            override fun onItemSelected(item: String) {
                val reasonType = BurnReason.getFromDescription(context, item)
                 viewModel.reason.postValue(reasonType!!)
            }
        })

        reasonDropDown.setAdapter(DefaultDropDrownAdapter(requireActivity(), BurnReason.getValues(context)))

        val aidTeamRadio = binding.aidRadio

        aidTeamRadio.setOnCheckedChangeListener { group, position ->
            val selectedId = group.checkedRadioButtonId

            when (selectedId) {
                R.id.aid_ok ->  viewModel.needsAidTeam.value = true
                R.id.aid_not_ok ->  viewModel.needsAidTeam.value = false
            }
        }

        viewModel.canStageOne.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }
}