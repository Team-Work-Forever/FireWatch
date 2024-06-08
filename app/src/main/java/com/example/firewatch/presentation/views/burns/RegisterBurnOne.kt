package com.example.firewatch.presentation.views.burns

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterBurnOneBinding
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.components.dropDown.DefaultDropDrownAdapter
import com.example.firewatch.presentation.components.dropDown.OnDropDownItemSelected
import com.example.firewatch.presentation.viewModels.burns.RegisterBurnViewModel
import com.example.firewatch.presentation.views.HomeActivity
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
        binding.data = RegisterBurnData

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        binding.datePicker.setOnDatePickClick { _, year, month, dayOfMonth ->
            RegisterBurnData.initDate.postValue(DateHelper.getLocalDateTime(year, month, dayOfMonth))
        }

        val typeDropDown = binding.typeDropDown
        typeDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
            override fun onItemSelected(item: String) {
                val burnType = BurnType.get(TypeValues.types.getValue(item))
                RegisterBurnData.type.postValue(burnType!!)
            }
        })
        typeDropDown.setAdapter(DefaultDropDrownAdapter(requireActivity(), TypeValues.types.keys.toTypedArray()))

        val reasonDropDown = binding.reasonDropDown
        reasonDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
            override fun onItemSelected(item: String) {
                val reasonType = BurnReason.get(TypeValues.reason.getValue(item))
                RegisterBurnData.reason.postValue(reasonType!!)
            }
        })

        reasonDropDown.setAdapter(DefaultDropDrownAdapter(requireActivity(), TypeValues.reason.keys.toTypedArray()))

        val aidTeamRadio = binding.aidRadio

        aidTeamRadio.setOnCheckedChangeListener { group, position ->
            val selectedId = group.checkedRadioButtonId

            when (selectedId) {
                R.id.aid_ok -> RegisterBurnData.needsAidTeam.value = true
                R.id.aid_not_ok -> RegisterBurnData.needsAidTeam.value = false
            }
        }

        return binding.root
    }
}