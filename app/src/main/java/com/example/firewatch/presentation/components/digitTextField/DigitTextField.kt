package com.example.firewatch.presentation.components.digitTextField

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.firewatch.databinding.DigitTextFieldBinding
import com.google.android.material.textfield.TextInputEditText

class DigitTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs){
    private val binding: DigitTextFieldBinding =
        DigitTextFieldBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val block1 = binding.digitBlock1.textInput
        val block2 = binding.digitBlock2.textInput
        val block3 = binding.digitBlock3.textInput
        val block4 = binding.digitBlock4.textInput

        setupEditText(block1, block2)
        setupEditText(block2, block3)
        setupEditText(block3, block4)
        setupEditText(block4, block1)
    }

    private fun setupEditText(currentEditText: TextInputEditText, nextEditText: TextInputEditText?) {
        currentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    nextEditText?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}