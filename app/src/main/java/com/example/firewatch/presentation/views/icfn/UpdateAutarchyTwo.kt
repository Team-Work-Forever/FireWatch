package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
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
        binding.lifecycleOwner = this

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

        viewModel.canStageTwo.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }

    private fun setUp() {
        setValueOn(binding.updateAutarchyStreet, viewModel.street, viewModel.autarchy.value?.address?.street)
        setValueOn(binding.updateAutarchyStreetNumber, viewModel.streetNumber, viewModel.autarchy.value?.address?.number.toString())
        setValueOn(binding.updateAutarchyZipCode, viewModel.zipCode, viewModel.autarchy.value?.address?.zipCode)
        setValueOn(binding.updateAutarchyCity, viewModel.city, viewModel.autarchy.value?.address?.city)
    }
}