package com.example.firewatch.presentation.views.sliders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.databinding.FragmentSlider1Binding
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.SliderViewModel

class Slider1 : HomeView<SliderViewModel>(SliderViewModel::class.java) {
    private lateinit var binding: FragmentSlider1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlider1Binding.inflate(layoutInflater)
        return binding.root
    }
}