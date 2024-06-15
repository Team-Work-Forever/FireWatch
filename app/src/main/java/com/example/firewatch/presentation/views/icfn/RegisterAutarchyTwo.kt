package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.firewatch.databinding.FragmentRegisterAutarchyTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.icfn.RegisterAutarchyViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class RegisterAutarchyTwo : Stage<RegisterAutarchyViewModel>(RegisterAutarchyViewModel::class.java) {
    private lateinit var binding: FragmentRegisterAutarchyTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterAutarchyTwoBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val swiper = binding.swiperHeader
        swiper.setTotalPage(totalPages)

        swiper.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        viewModel.canStageTwo.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }
}