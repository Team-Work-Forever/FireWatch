package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentUpdateAutarchyTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.icfn.UpdateAutarchyViewModel
import com.example.firewatch.shared.extensions.getProblem
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class UpdateAutarchyTwo : Stage<UpdateAutarchyViewModel>(UpdateAutarchyViewModel::class.java) {
    private lateinit var binding: FragmentUpdateAutarchyTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateAutarchyTwoBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        val swiper = binding.swiperHeader
        swiper.setTotalPage(totalPages)

        setUp()

        swiper.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
           exit()
        }

        binding.continueBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val result = viewModel.updateById(UpdateAutarchyViewModel.id).await()

                if (result.isSuccess) {
                    Toast.makeText(requireActivity(), result.getOrThrow(), Toast.LENGTH_LONG).show()
                    return@launch exit()
                }

                Toast.makeText(requireActivity(), result.getProblem(), Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun setUp() {
        binding.updateAutarchyStreet.setText(viewModel.autarchy.value?.address?.street)
        binding.updateAutarchyStreetNumber.setText(viewModel.autarchy.value?.address?.number.toString())
        binding.updateAutarchyZipCode.setText(viewModel.autarchy.value?.address?.zipCode)
        binding.updateAutarchyCity.setText(viewModel.autarchy.value?.address?.city)
    }
}