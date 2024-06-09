package com.example.firewatch.presentation.views.auth.stages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firewatch.databinding.FragmentRegisterStageOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.auth.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import java.io.File

@AndroidEntryPoint
@WithFragmentBindings
class RegisterStageOne : Stage<RegisterViewModel>(RegisterViewModel::class.java) {
    private lateinit var binding: FragmentRegisterStageOneBinding
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
        binding = FragmentRegisterStageOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        binding.pickAvatar.setOnClickListener {
            pickAvatarResult.launch("image/*")
        }

        header.setOnBackListener {
            exit()
        }

        val button = binding.continueBtn
        button.setOnClickListener {
            next()
        }

        return binding.root
    }
}