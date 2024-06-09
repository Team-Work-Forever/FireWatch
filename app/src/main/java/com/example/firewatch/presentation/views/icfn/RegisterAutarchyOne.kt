package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firewatch.databinding.FragmentRegisterAutarchyOneBinding
import com.example.firewatch.databinding.FragmentRegisterBurnOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.icfn.RegisterAutarchyViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import java.io.File

@AndroidEntryPoint
@WithFragmentBindings
class RegisterAutarchyOne : Stage<RegisterAutarchyViewModel>(RegisterAutarchyViewModel::class.java) {
    private lateinit var binding: FragmentRegisterAutarchyOneBinding
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
        binding = FragmentRegisterAutarchyOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        val swiper = binding.swiperHeader
        swiper.setTotalPage(totalPages)

        binding.pickAvatar.setOnClickListener {
            pickAvatarResult.launch("image/*")
        }

        swiper.setOnBackListener {
            exit()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        return binding.root
    }
}