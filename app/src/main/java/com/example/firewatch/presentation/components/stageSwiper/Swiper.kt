package com.example.firewatch.presentation.components.stageSwiper

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.firewatch.databinding.FragmentSwiperBinding
import com.example.firewatch.presentation.adapters.Stage

interface SwiperPage {
    fun next()
    fun back()
}

class Swiper @JvmOverloads() constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), SwiperPage {
    private var fragmentActivity: FragmentActivity = context as FragmentActivity
    private var binding: FragmentSwiperBinding
    private var swiperAdapter: SwiperAdapter
    private var swiper: ViewPager2

    val pageCount: Int
        get() = swiperAdapter.itemCount

    init {
        binding = FragmentSwiperBinding.inflate(LayoutInflater.from(context), this, true)

        swiper = binding.swiper
        swiperAdapter = SwiperAdapter(fragmentActivity, this)

        // Configure Swiper
        swiper.adapter = swiperAdapter
        swiper.setUserInputEnabled(false);
    }

    fun  addPages(pages: List<Class<out Stage<*>>>) {
        swiperAdapter.updatePages(pages)
    }

    override fun next() {
        if (swiper.currentItem > swiperAdapter.itemCount) return

        swiper.setCurrentItem(swiper.currentItem + 1, true)
    }

    override fun back() {
        if (swiper.currentItem == 0) return

        swiper.setCurrentItem(swiper.currentItem - 1, true)
    }
}