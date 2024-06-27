package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.firewatch.R
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.adapters.homeView.HomeViewAdapter
import com.example.firewatch.shared.helpers.BaseActivity

open class ViewPagerNavigationActivity(slides: List<Class<out HomeView<*>>>) : BaseActivity() {
    val sliderViews: List<Class<out HomeView<*>>> = slides
    val slideViewSize = sliderViews.size
    val instanceViews = mutableListOf<HomeView<*>>()

    protected lateinit var viewSlider: ViewPager2

    val adapter = HomeViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewSlider = findViewById(R.id.viewSlider)
        viewSlider.adapter = HomeViewAdapter(this)
    }
}
