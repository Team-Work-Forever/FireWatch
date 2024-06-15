package com.example.firewatch.presentation.views.auth.stages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.firewatch.databinding.FragmentRegisterStageTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.auth.RegisterViewModel
import com.example.firewatch.shared.extensions.addValidators
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class RegisterStageTwo : Stage<RegisterViewModel>(RegisterViewModel::class.java) {
    private lateinit var binding: FragmentRegisterStageTwoBinding
    private lateinit var canStageTwoComplete: LiveData<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        canStageTwoComplete = MediatorLiveData<Boolean>().apply {
            addValidators(listOf(
                viewModel.emailValidator,
                viewModel.streetValidator,
                viewModel.streetNumberValidator,
                viewModel.zipCodeValidator,
                viewModel.cityValidator
            ))
        }

        binding = FragmentRegisterStageTwoBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        canStageTwoComplete.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }
}