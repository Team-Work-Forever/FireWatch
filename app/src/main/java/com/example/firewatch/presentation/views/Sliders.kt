package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivitySlidersBinding
import com.example.firewatch.presentation.viewModels.SliderViewModel
import com.example.firewatch.presentation.viewModels.SplashViewModel
import com.example.firewatch.presentation.views.sliders.Slider1
import com.example.firewatch.presentation.views.sliders.Slider2
import com.example.firewatch.shared.helpers.BaseActivity

class Sliders : ViewPagerNavigationActivity(listOf(
    Slider1::class.java,
    Slider2::class.java
)) {
    private lateinit var binding: ActivitySlidersBinding
    private lateinit var points: List<RadioButton>
    private val viewModel: SliderViewModel by viewModels()

    companion object {
        fun new(context: Context) {
            val intent = Intent(context, Sliders::class.java).apply {
                flags = flags or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sliders)
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        points = listOf(
            binding.slider1,
            binding.slider2
        )

        points[0].isChecked = true
        points.forEachIndexed { index, radioButton ->
            radioButton.setOnClickListener {
                clearOptions()

                viewSlider.currentItem = index
                points[index].isChecked = true
            }
        }

        binding.viewSlider.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                clearOptions()
                points[position].isChecked = true
            }
        })

        binding.btnGo.setOnClickListener {
            viewModel.goForward(this)
        }
    }

    private fun clearOptions() {
         points.forEach {
            it.isChecked = false
        }
    }
}