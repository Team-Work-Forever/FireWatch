package com.example.firewatch.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firewatch.presentation.views.HomeActivity

class HomeViewAdapter(val activity: HomeActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = activity.slideViewSize

    override fun createFragment(position: Int): Fragment {
        val view = activity.sliderViews[position]
        return HomeView.new(view, position)
    }
}