package com.example.firewatch.presentation.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firewatch.presentation.components.stageSwiper.SwiperPage

open class Stage<TViewModel : ViewModel>(
    private val clazz: Class<TViewModel>
) : Fragment(), SwiperPage {
    val viewModel: TViewModel by lazy { ViewModelProvider(requireActivity())[clazz] }
    var swiper: SwiperPage? = null
    protected var totalPages: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getInt("totalPages").let { totalPages = it }
        }
    }

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
            position: Int,
            totalPages: Int,
        ): Stage<*>
            where TStage : Stage<*> {
                val stage = clazz.getDeclaredConstructor().newInstance().apply {
                    arguments = Bundle().apply {
                        putInt("currentPage", position)
                        putInt("totalPages", totalPages)
                    }
                }

                stage.swiper = swiper
                return stage
            }
    }
}