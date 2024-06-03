package com.example.firewatch.presentation.views.stages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterStageOneBinding
import com.example.firewatch.databinding.FragmentRegisterStageThreeBinding

class RegisterStageThree : Stage() {
    private lateinit var binding: FragmentRegisterStageThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterStageThreeBinding.inflate(layoutInflater)
        val header = binding.swiperHeader

        header.setOnBackListener {
            back()
        }


        binding.continueBtn.setOnClickListener {
            next()
        }
        return binding.root
    }
}