package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.firewatch.R
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.adapters.homeView.HomeViewAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

open class BottomNavigationActivity(slides: List<Class<out HomeView<*>>>) : AppCompatActivity() {
    val sliderViews: List<Class<out HomeView<*>>> = slides
    val slideViewSize = sliderViews.size

    protected lateinit var bottomNavigation: BottomNavigationView
    protected lateinit var viewSlider: ViewPager2

    val adapter = HomeViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewSlider = findViewById(R.id.viewSlider)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        viewSlider.adapter = HomeViewAdapter(this)
    }

    fun setMenuItemSelected(listener: NavigationBarView.OnItemSelectedListener) {
        bottomNavigation.setOnItemSelectedListener(listener)
    }
}