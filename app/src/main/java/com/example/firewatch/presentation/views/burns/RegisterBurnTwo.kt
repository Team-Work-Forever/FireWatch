package com.example.firewatch.presentation.views.burns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.databinding.FragmentRegisterBurnTwoBinding
import com.example.firewatch.databinding.FragmentRegisterStageTwoBinding
import com.example.firewatch.databinding.FragmentUpdateBurnTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.burns.RegisterBurnViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class RegisterBurnTwo : Stage<RegisterBurnViewModel>(RegisterBurnViewModel::class.java) {
    private lateinit var binding: FragmentRegisterBurnTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBurnTwoBinding.inflate(layoutInflater)
        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        return binding.root
    }
}