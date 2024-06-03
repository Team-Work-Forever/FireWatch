package com.example.firewatch.presentation.components.stageSwiper

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.example.firewatch.R
import com.example.firewatch.databinding.SwiperBodyBinding
import com.example.firewatch.presentation.components.textField.TextFieldType

class SwiperBody @JvmOverloads() constructor(
    context: Context,
    attrs: AttributeSet? = null
): LinearLayout(context, attrs) {
    private var binding: SwiperBodyBinding
    private var headerNumber: TextView
    private var backButton: ImageButton

    init {
        binding = SwiperBodyBinding.inflate(LayoutInflater.from(context), this, true)
        headerNumber = binding.swiperHeaderNumPage
        backButton = binding.backBtn


        attrs?.let {
            val attributes: TypedArray = context.obtainStyledAttributes(
                it, R.styleable.SwiperBody, 0, 0
            )

            val position = attributes.getInt(R.styleable.SwiperBody_position, 1)

            headerNumber.text = position.toString()
            attributes.recycle()
        }
    }

    fun setOnBackListener(l: OnClickListener?) {
        l?.let {
            backButton.setOnClickListener(l)
        }
    }

    fun setPageNumber(number: Int) {
        headerNumber.text = number.toString()
    }
}