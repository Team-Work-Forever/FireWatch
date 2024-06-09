package com.example.firewatch.presentation.views.auth.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentForgotPasswordTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.auth.ForgotPasswordViewModel
import com.example.firewatch.presentation.views.SwiperActivity
import com.example.firewatch.presentation.views.profile.UpdateProfileOne
import com.example.firewatch.presentation.views.profile.UpdateProfileTwo
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class ForgotPasswordTwo : Stage<ForgotPasswordViewModel>(ForgotPasswordViewModel::class.java) {
    private lateinit var binding: FragmentForgotPasswordTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordTwoBinding.inflate(inflater)
        binding.viewModel = viewModel

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.forgotAvatarPicture)

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
           viewLifecycleOwner.lifecycleScope.launch {
               val resetResult = viewModel.resetPassword().await()

               if (resetResult.isSuccess) {
                   Toast.makeText(requireActivity(), resetResult.getOrThrow(), Toast.LENGTH_LONG).show()
                   return@launch exit()
               }

               Toast.makeText(requireActivity(), resetResult.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
           }
        }

        return binding.root
    }
}