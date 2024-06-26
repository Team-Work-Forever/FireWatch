package com.example.firewatch.presentation.components.stageSwiper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firewatch.presentation.adapters.Stage

class SwiperAdapter(
    activity: FragmentActivity,
    val swiper: Swiper,
) : FragmentStateAdapter(activity) {
    private val pages: MutableList<Class<out Stage<*>>> = mutableListOf()

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        val stageClass = pages[position]
        val stage = Stage.new(stageClass, swiper, position, itemCount)

        return stage
    }

    fun updatePages(list: List<Class<out Stage<*>>>) {
        pages.addAll(list)
        this.notifyDataSetChanged()
    }
}