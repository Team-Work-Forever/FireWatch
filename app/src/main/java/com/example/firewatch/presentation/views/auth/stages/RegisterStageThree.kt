package com.example.firewatch.presentation.views.auth.stages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentRegisterStageThreeBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.auth.RegisterViewModel
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class RegisterStageThree : Stage<RegisterViewModel>(RegisterViewModel::class.java) {
    private lateinit var binding: FragmentRegisterStageThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterStageThreeBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
              viewLifecycleOwner.lifecycleScope.launch {
                  val result = viewModel.registerUser().await()

                  if (result.isSuccess) {
                      Toast.makeText(requireActivity(), "User Registered!", Toast.LENGTH_LONG).show()
                      return@launch exit()
                  }

                  Toast.makeText(requireActivity(), result.getProblem(), Toast.LENGTH_LONG).show()
              }
        }

        return binding.root
    }
}