package com.example.firewatch.presentation.views.auth.stages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterStageThreeBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.auth.RegisterViewModel
import com.example.firewatch.shared.extensions.addValidator
import com.example.firewatch.shared.extensions.addValidators
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class RegisterStageThree : Stage<RegisterViewModel>(RegisterViewModel::class.java) {
    private lateinit var binding: FragmentRegisterStageThreeBinding
    private lateinit var canStageThreeComplete: LiveData<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        canStageThreeComplete = MediatorLiveData<Boolean>().apply {
            addValidators(listOf(
                viewModel.nifValidator,
                viewModel.passwordValidator,
                viewModel.confirmPasswordValidator,
                viewModel.rgpdValidator
            ))
        }

        binding = FragmentRegisterStageThreeBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            back()
        }

        binding.checkboxMeat.setOnCheckedChangeListener { _, isChecked ->
            binding.txtPassword.showPassword = isChecked
            binding.txtConfirmPassword.showPassword = isChecked
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

        binding.registerStage3Dda.setOnCheckedChangeListener { group, position ->
            val selectedId = group.checkedRadioButtonId

            when (selectedId) {
                R.id.register_stage_3_yes -> viewModel.rgpd.value = true
                R.id.register_stage_3_no -> viewModel.rgpd.value = false
            }
        }

        canStageThreeComplete.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }
}