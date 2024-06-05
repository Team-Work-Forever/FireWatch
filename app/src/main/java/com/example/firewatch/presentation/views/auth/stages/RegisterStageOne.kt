package com.example.firewatch.presentation.views.auth.stages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.MainActivity
import com.example.firewatch.databinding.FragmentRegisterStageOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import java.io.File

@AndroidEntryPoint
@WithFragmentBindings
class RegisterStageOne : Stage<RegisterViewModel>(RegisterViewModel::class.java) {
    private lateinit var binding: FragmentRegisterStageOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterStageOneBinding.inflate(layoutInflater)
        binding.data = RegisterSignUserData
        val header = binding.swiperHeader

        val file = File(requireActivity().cacheDir, "FireDeadshot.png")
        file.createNewFile()
        file.outputStream().use {
            requireActivity().assets.open("FireDeadshot.png").copyTo(it)
        }

        RegisterSignUserData.avatarFile.postValue(file)

        header.setOnBackListener {
            val intent = Intent(requireActivity(), MainActivity::class.java);
            startActivity(intent);
        }

        val button = binding.continueBtn
        button.setOnClickListener {
            next()
        }

        return binding.root
    }
}