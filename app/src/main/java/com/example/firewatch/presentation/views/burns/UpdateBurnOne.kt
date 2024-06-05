package com.example.firewatch.presentation.views.burns

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentUpdateBurnOneBinding
import com.example.firewatch.databinding.FragmentUpdateProfileOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.burns.UpdateBurnViewModel
import com.example.firewatch.presentation.viewModels.profile.UpdateProfileViewModel
import com.example.firewatch.presentation.views.EditDetailBurn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class UpdateBurnOne : Stage<UpdateBurnViewModel>(UpdateBurnViewModel::class.java) {
    private lateinit var binding: FragmentUpdateBurnOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBurnOneBinding.inflate(layoutInflater)
        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener() {
            val intent = Intent(requireActivity(), EditDetailBurn::class.java)
            startActivity(intent)
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        return binding.root
    }
}