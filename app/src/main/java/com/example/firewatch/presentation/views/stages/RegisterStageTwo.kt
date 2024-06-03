package com.example.firewatch.presentation.views.stages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterStageOneBinding
import com.example.firewatch.databinding.FragmentRegisterStageTwoBinding

class RegisterStageTwo : Stage() {
    private lateinit var binding: FragmentRegisterStageTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterStageTwoBinding.inflate(layoutInflater)
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