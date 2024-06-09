package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterAutarchyThreeBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.icfn.RegisterAutarchyViewModel
import com.example.firewatch.shared.extensions.getProblem
import kotlinx.coroutines.launch

class RegisterAutarchyThree : Stage<RegisterAutarchyViewModel>(RegisterAutarchyViewModel::class.java) {
    private lateinit var binding: FragmentRegisterAutarchyThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterAutarchyThreeBinding.inflate(layoutInflater)

        val swiper = binding.swiperHeader
        swiper.setTotalPage(totalPages)

        swiper.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val autarchy = viewModel.create().await()

                if (autarchy.isSuccess) {
                    return@launch exit()
                }

                Toast.makeText(requireActivity(), autarchy.getProblem(), Toast.LENGTH_LONG).show()
            }
        }


        return binding.root
    }
}