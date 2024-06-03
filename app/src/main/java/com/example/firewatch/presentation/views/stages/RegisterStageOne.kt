package com.example.firewatch.presentation.views.stages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.MainActivity
import com.example.firewatch.databinding.FragmentRegisterStageOneBinding

class RegisterStageOne : Stage() {
    private lateinit var binding: FragmentRegisterStageOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterStageOneBinding.inflate(layoutInflater)
        val header = binding.swiperHeader

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