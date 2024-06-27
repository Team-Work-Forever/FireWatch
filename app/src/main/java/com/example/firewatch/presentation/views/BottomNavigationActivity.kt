package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.firewatch.R
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BottomNavigationActivity(slides: List<Class<out HomeView<*>>>) : ViewPagerNavigationActivity(slides) {
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavigation = findViewById(R.id.bottomNavigation)
    }

    fun setMenuItemSelected(options: Map<Int, Int>) {
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            var id = options[menuItem.itemId]

            if (id == null) {
                id = 0
            }

            viewSlider.setCurrentItem(id, true)
            true
        }

        viewSlider.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val key = options.entries.firstOrNull { it.value == position }?.key

                if (key == null) {
                    return super.onPageSelected(position)
                }

                bottomNavigation.selectedItemId = key

                instanceViews.getOrNull(position)?.onPageRefresh()
                super.onPageSelected(position)
            }
        })
    }
}