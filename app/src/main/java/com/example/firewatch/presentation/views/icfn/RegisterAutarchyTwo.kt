package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterAutarchyTwoBinding
import com.example.firewatch.databinding.FragmentRegisterStageOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.icfn.RegisterAutarchyViewModel
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch


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

        val swiper = binding.swiperHeader
        swiper.setTotalPage(totalPages)

        swiper.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        return binding.root
    }
}