package com.example.firewatch.presentation.views.burns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentUpdateBurnTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.burns.UpdateBurnViewModel
import com.example.firewatch.presentation.views.DetailBurnActivity
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

        binding.continueBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (viewModel.updateBurn(UpdateBurnViewModel.id).await()) {
                    DetailBurnActivity.new(requireActivity(), UpdateBurnViewModel.id)
                }
            }
        }

        return binding.root
    }
}