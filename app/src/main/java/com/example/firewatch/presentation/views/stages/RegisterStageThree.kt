package com.example.firewatch.presentation.views.stages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.firewatch.databinding.FragmentRegisterStageThreeBinding
import com.example.firewatch.presentation.viewModels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class RegisterStageThree : Stage<RegisterViewModel>(RegisterViewModel::class.java) {
    private lateinit var binding: FragmentRegisterStageThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterStageThreeBinding.inflate(layoutInflater)
        binding.data = RegisterSignUserData
        val header = binding.swiperHeader

        header.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            viewModel.registerUser()
        }

        return binding.root
    }
}