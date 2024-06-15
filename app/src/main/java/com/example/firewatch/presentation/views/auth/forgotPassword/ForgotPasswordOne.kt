package com.example.firewatch.presentation.views.auth.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentForgotPasswordOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.auth.ForgotPasswordViewModel
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class ForgotPasswordOne : Stage<ForgotPasswordViewModel>(ForgotPasswordViewModel::class.java) {
    private lateinit var binding: FragmentForgotPasswordOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewLifecycleOwner.lifecycleScope.launch {
            val canSendNotice = viewModel.sendForgotNotice().await()

            if (canSendNotice.isFailure) {
                Toast.makeText(context, canSendNotice.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
                exit()
            }

            Toast.makeText(context, canSendNotice.getOrThrow(), Toast.LENGTH_LONG).show()
        }

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.forgotAvatarPicture)

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            exit()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        binding.forgotCode.digitInput.observe(viewLifecycleOwner, Observer { input ->
            viewModel.forgotCode.postValue(input)
        })

        viewModel.canStageOne.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }
}
