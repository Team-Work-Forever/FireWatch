package com.example.firewatch.presentation.views.burns

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterBurnOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.burns.RegisterBurnViewModel
import com.example.firewatch.presentation.views.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class RegisterBurnOne : Stage<RegisterBurnViewModel>(RegisterBurnViewModel::class.java) {
    private lateinit var binding: FragmentRegisterBurnOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBurnOneBinding.inflate(inflater)
        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        return binding.root
    }
}