package com.example.firewatch.presentation.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

open class HomeView<TViewModel>(clazz: Class<TViewModel>) : Fragment() where TViewModel : ViewModel {
    protected val viewModel: TViewModel by lazy { ViewModelProvider(this)[clazz] }

    companion object {
        @JvmStatic
        inline fun <reified THomeView> new(
            clazz: Class<out THomeView>,
            position: Int
        ): HomeView<*>
                where THomeView : HomeView<*> {
            val stage = clazz.getDeclaredConstructor().newInstance().apply {
                arguments = Bundle().apply {
                    putInt("currentPage", position)
                }
            }

            return stage
        }
    }
}