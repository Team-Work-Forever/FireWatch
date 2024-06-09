package com.example.firewatch.presentation.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firewatch.databinding.FragmentUpdateProfileOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.profile.UpdateProfileViewModel
import com.example.firewatch.shared.helpers.ImageHelper
import com.example.firewatch.shared.helpers.SwiperViews
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import java.io.File

@AndroidEntryPoint
@WithFragmentBindings
class UpdateProfileOne : Stage<UpdateProfileViewModel>(UpdateProfileViewModel::class.java) {
    private lateinit var binding: FragmentUpdateProfileOneBinding
    private var pickAvatarResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.pickAvatar.setImageURI(it)

        if (it == null) return@registerForActivityResult

        val file = File(requireActivity().cacheDir, "profile.png")
        file.createNewFile()

        requireActivity().contentResolver.openInputStream(it)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        viewModel.avatarFile.postValue(file)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        setUp()

        val swiperHeader = binding.swiperHeader
        swiperHeader.setTotalPage(totalPages)
        swiperHeader.setOnBackListener {
            exit()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        binding.profileChangePasswordTxt.setOnClickListener {
           viewModel.authUser?.let {
               SwiperViews.forgotPassword(requireActivity(), it.email)
           }
        }

        return binding.root
    }

    private fun setUp() {
        binding.updateProfileNif.setText(viewModel.authUser?.userName)
        binding.updateProfilePhoneNumber.setText(viewModel.authUser?.phone?.number.toString())
        binding.updateProfileUserName.setText(viewModel.authUser?.userName)

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.pickAvatar)

        binding.pickAvatar.setOnClickListener {
            pickAvatarResult.launch("image/*")
        }
    }
}