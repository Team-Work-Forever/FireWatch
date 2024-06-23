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
    private var totalPage: TextView
    private var backButton: ImageButton
    private var titleHeader: TextView

    init {
        binding = SwiperBodyBinding.inflate(LayoutInflater.from(context), this, true)
        headerNumber = binding.swiperHeaderNumPage
        backButton = binding.backBtn
        totalPage = binding.swiperTotalPages
        titleHeader = binding.swiperTitle

        attrs?.let {
            val attributes: TypedArray = context.obtainStyledAttributes(
                it, R.styleable.SwiperBody, 0, 0
            )

            val position = attributes.getInt(R.styleable.SwiperBody_position, 1)
            val title = attributes.getText(R.styleable.SwiperBody_header)

            headerNumber.text = position.toString()
            titleHeader.text = title

            attributes.recycle()
        }
    }

    fun setTitle(title: String) {
        titleHeader.text = title
    }

    fun setOnBackListener(l: OnClickListener?) {
        l?.let {
            backButton.setOnClickListener(l)
        }
    }

    fun setTotalPage(value: Int) {
        "/${value}".also { totalPage.text = it }
    }

    fun setPageNumber(number: Int) {
        headerNumber.text = number.toString()
    }
}