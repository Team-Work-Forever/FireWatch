package com.example.firewatch.presentation.views.stages

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firewatch.presentation.components.stageSwiper.SwiperPage

open class Stage<TViewModel : ViewModel>(
    private val clazz: Class<TViewModel>
) : Fragment(), SwiperPage {
    val viewModel: TViewModel by lazy { ViewModelProvider(this)[clazz] }
    var swiper: SwiperPage? = null

    override fun next() {
        swiper?.next()
    }

    override fun back() {
        swiper?.back()
    }

    companion object {
        @JvmStatic
        inline fun <reified TStage> new(
            clazz: Class<out TStage>,
            swiper: SwiperPage,
            position: Int
        ): Stage<*>
            where TStage : Stage<*> {
                val stage = clazz.getDeclaredConstructor().newInstance().apply {
                    arguments = Bundle().apply {
                        putInt("currentPage", position)
                    }
                }

                stage.swiper = swiper
                return stage
            }
    }
}