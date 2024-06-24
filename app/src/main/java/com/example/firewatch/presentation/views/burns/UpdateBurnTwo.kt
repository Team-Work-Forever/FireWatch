package com.example.firewatch.presentation.views.burns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentUpdateBurnTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.burns.UpdateBurnViewModel
import com.example.firewatch.presentation.viewModels.burns.UpdateState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class UpdateBurnTwo : Stage<UpdateBurnViewModel>(UpdateBurnViewModel::class.java) {
    private lateinit var binding: FragmentUpdateBurnTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBurnTwoBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            back()
        }

        setUp()

        val continueBtn = binding.continueBtn
        when (UpdateBurnViewModel.state) {
            UpdateState.UPDATE -> {
                continueBtn.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        if (viewModel.updateBurn(UpdateBurnViewModel.id).await()) {
                            exit()
                        }
                    }
                }
            }
            UpdateState.REPEAT -> {
                header.setTitle(getString(R.string.repeat))

                continueBtn.text = getString(R.string.repeat)
                continueBtn.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        if (viewModel.create().await()) {
                            exit()
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun setUp() {
        "Lat: ${viewModel.burn.value!!.coordinates.lat.toPlainString()}".also {
            binding.updateBurnLat.text = it
        }

        "Lon: ${viewModel.burn.value!!.coordinates.lon.toPlainString()}".also {
            binding.updateBurnLon.text = it
        }
    }
}