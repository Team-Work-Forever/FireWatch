package com.example.firewatch.presentation.views.burns

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterBurnOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.components.dropDown.DefaultDropDrownAdapter
import com.example.firewatch.presentation.components.dropDown.OnDropDownItemSelected
import com.example.firewatch.presentation.viewModels.burns.RegisterBurnViewModel
import com.example.firewatch.presentation.views.HomeActivity
import com.example.firewatch.shared.helpers.DateHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class RegisterBurnOne : Stage<RegisterBurnViewModel>(RegisterBurnViewModel::class.java) {
    private lateinit var binding: FragmentRegisterBurnOneBinding

    private val types = mapOf(
        Pair("Burn", "burn")
    )

    private val reason = mapOf(
        Pair("Others", "others"),
        Pair("Sanitary Burn", "sanitaryBurn"),
        Pair("Agricultural Waste Management", "agritoralWasteManagement"),
        Pair("Forestry Waste Management", "forestryWasteManagement"),
        Pair("Bush Management", "bushManagement")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBurnOneBinding.inflate(inflater)
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
            DateHelper.getFormattedDate(year, month, dayOfMonth)
        }

        val typeDropDown = binding.typeDropDown
        typeDropDown.setAdapter(DefaultDropDrownAdapter(requireActivity(), types.keys.toTypedArray()))

        typeDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
            override fun onItemSelected(item: String) {
                println(item)
            }
        })

        val reasonDropDown = binding.reasonDropDown
        reasonDropDown.setAdapter(DefaultDropDrownAdapter(requireActivity(), reason.keys.toTypedArray()))

        reasonDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
            override fun onItemSelected(item: String) {
                println(item)
            }
        })

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