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

    protected lateinit var viewSlider: ViewPager2

    val adapter = HomeViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewSlider = findViewById(R.id.viewSlider)
        viewSlider.adapter = HomeViewAdapter(this)
    }

//    open fun setMenuItemSelected(options: Map<Int, Int>) {
//        viewSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                val key = options.entries.firstOrNull { it.value == position }?.key
//
//                if (key == null) {
//                    return super.onPageSelected(position)
//                }
//
//                super.onPageSelected(position)
//            }
//        })
//    }
}
