package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityHomeBinding
import com.example.firewatch.presentation.viewModels.home.HomeViewModel
import com.example.firewatch.presentation.views.views.HomeInsertBurn
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.adapters.homeView.HomeViewAdapter
import com.example.firewatch.presentation.views.views.ActiveBurns
import com.example.firewatch.presentation.views.views.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var viewSlider: ViewPager2
    private var currentPage = 0

    val sliderViews: List<Class<out HomeView<*>>>
        get() = listOf(
            HomeInsertBurn::class.java,
            ActiveBurns::class.java,
            Profile::class.java
        )

    val slideViewSize = sliderViews.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = homeViewModel

        bottomNavigation = binding.bottomNavigation
        viewSlider = binding.viewSlider

        viewSlider.adapter = HomeViewAdapter(this)
    }
}