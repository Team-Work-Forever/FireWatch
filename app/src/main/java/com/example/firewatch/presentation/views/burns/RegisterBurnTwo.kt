package com.example.firewatch.presentation.views.burns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentRegisterBurnTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.burns.RegisterBurnViewModel
import com.example.firewatch.presentation.views.CheckBurnAvailability
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch
import java.math.BigDecimal

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

        viewModel.lat.postValue(BigDecimal("40.64144757655504"))
        viewModel.lon.postValue(BigDecimal("-7.748367723642718"))

        binding.continueBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val checkStatus = viewModel.create().await()
                CheckBurnAvailability.new(requireActivity(), checkStatus)
            }
        }

        return binding.root
    }
}