package com.example.firewatch.presentation.views.stages

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.firewatch.presentation.components.stageSwiper.SwiperPage

open class Stage() : Fragment(), SwiperPage {
    var swiper: SwiperPage? = null

    override fun next() {
        swiper?.next()
    }

    override fun back() {
        swiper?.back()
    }

    companion object {
        @JvmStatic
        inline fun <reified TStage> new(clazz: Class<out TStage>, swiper: SwiperPage, position: Int): Stage
            where TStage : Stage {
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