package com.example.firewatch.presentation.components.digitTextField

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.firewatch.databinding.DigitBlockBinding

class DigitBlock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding: DigitBlockBinding =
        DigitBlockBinding.inflate(LayoutInflater.from(context), this, true)

    val textInput = binding.textInputEdit
}