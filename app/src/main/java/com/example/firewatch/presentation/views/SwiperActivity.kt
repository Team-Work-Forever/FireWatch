package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivitySwiperBinding
import com.example.firewatch.presentation.adapters.Stage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SwiperActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwiperBinding

    companion object {
        private const val PAGES_ARGUMENT = "pages"

        fun create(activity: Context,  pages: List<Class<out Stage<*>>>) {
            val intent = Intent(activity, SwiperActivity::class.java).apply {
                putExtra(PAGES_ARGUMENT, pages.toTypedArray())
            }

            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_swiper)
        val pages = intent.getSerializableExtra(PAGES_ARGUMENT) as Array<Class<out Stage<*>>>

        val swiper = binding.swiper
        swiper.addPages(pages.toList())
    }
}