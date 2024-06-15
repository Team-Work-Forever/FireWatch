package com.example.firewatch.presentation.views.burns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterBurnOneBinding
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.components.dropDown.DefaultDropDrownAdapter
import com.example.firewatch.presentation.components.dropDown.OnDropDownItemSelected
import com.example.firewatch.presentation.viewModels.burns.RegisterBurnViewModel
import com.example.firewatch.shared.helpers.DateHelper
import com.example.firewatch.shared.helpers.TypeValues
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class RegisterBurnOne : Stage<RegisterBurnViewModel>(RegisterBurnViewModel::class.java) {
    private lateinit var binding: FragmentRegisterBurnOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBurnOneBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
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
                val burnType = BurnType.get(TypeValues.types.getValue(item))
                viewModel.type.postValue(burnType!!)
            }
        })
        typeDropDown.setAdapter(DefaultDropDrownAdapter(requireActivity(), TypeValues.types.keys.toTypedArray()))

        val reasonDropDown = binding.reasonDropDown
        reasonDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
            override fun onItemSelected(item: String) {
                val reasonType = BurnReason.get(TypeValues.reason.getValue(item))
                viewModel.reason.postValue(reasonType!!)
            }
        })

        reasonDropDown.setAdapter(DefaultDropDrownAdapter(requireActivity(), TypeValues.reason.keys.toTypedArray()))

        val aidTeamRadio = binding.aidRadio

        aidTeamRadio.setOnCheckedChangeListener { group, position ->
            val selectedId = group.checkedRadioButtonId

            when (selectedId) {
                R.id.aid_ok -> viewModel.needsAidTeam.value = true
                R.id.aid_not_ok -> viewModel.needsAidTeam.value = false
            }
        }

        viewModel.canStageOne.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }
}