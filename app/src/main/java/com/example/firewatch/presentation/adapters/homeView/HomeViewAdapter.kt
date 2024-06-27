package com.example.firewatch.presentation.adapters.homeView

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firewatch.presentation.views.BottomNavigationActivity
import com.example.firewatch.presentation.views.HomeActivity
import com.example.firewatch.presentation.views.ViewPagerNavigationActivity

class HomeViewAdapter(val activity: ViewPagerNavigationActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = activity.slideViewSize

    override fun createFragment(position: Int): Fragment {
        val view = activity.sliderViews[position]
        return HomeView.new(view, position)
    }
}