package com.example.firewatch.presentation.views.sliders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentSlider1Binding
import com.example.firewatch.databinding.FragmentSlider2Binding
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.SliderViewModel
import com.example.firewatch.shared.helpers.BaseFragment

class Slider2 : HomeView<SliderViewModel>(SliderViewModel::class.java) {
    private lateinit var binding: FragmentSlider2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlider2Binding.inflate(layoutInflater)

        return binding.root
    }
}