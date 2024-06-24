package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.firewatch.R
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.adapters.homeView.HomeViewAdapter
import com.example.firewatch.shared.helpers.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

open class BottomNavigationActivity(slides: List<Class<out HomeView<*>>>) : BaseActivity() {
    val sliderViews: List<Class<out HomeView<*>>> = slides
    val slideViewSize = sliderViews.size

    private lateinit var bottomNavigation: BottomNavigationView
    protected lateinit var viewSlider: ViewPager2

    val adapter = HomeViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewSlider = findViewById(R.id.viewSlider)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        viewSlider.adapter = HomeViewAdapter(this)
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
                super.onPageSelected(position)
            }
        })
    }
}