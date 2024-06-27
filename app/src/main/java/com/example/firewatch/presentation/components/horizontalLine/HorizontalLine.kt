package com.example.firewatch.presentation.components.horizontalLine

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.compose.ui.res.booleanResource
import com.example.firewatch.R
import com.example.firewatch.databinding.HorizontalLineBinding

interface OnSelectedItemCallBack {
    fun onSelectedItem(position: Int)
}

class HorizontalLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs){
    private var currentSelected: Int = 0
    private var onSelectedItemCallBack: OnSelectedItemCallBack? = null

    private val binding: HorizontalLineBinding =
        HorizontalLineBinding.inflate(LayoutInflater.from(context), this, true)

    private val options = mapOf(
        Pair(binding.horizontalActive, 0),
        Pair(binding.horizontalScheduled, 1),
        Pair(binding.horizontalCompleted, 2),
    )

    init {
        selectItem(binding.horizontalActive)

        options.forEach { entry ->
            entry.key.setOnClickListener {
                selectItem(entry.key)
                onSelectedItemCallBack?.onSelectedItem(currentSelected)
            }
        }
    }

    fun selectItemOnIndex(index: Int) {
        options.forEach { (button, position) ->
            if (position == index) {
                button.callOnClick()
            }
        }
    }

    private fun selectItem(button: AppCompatButton) {
        options.forEach { entry ->
            val currentOption = entry.key

            if (currentOption != button) {
                return@forEach currentOption.setBackgroundColor(resources.getColor(R.color.middle_gray))
            }

            currentSelected = options[currentOption] ?: 0
            currentOption.setBackgroundColor(resources.getColor(R.color.orange))
        }
    }

    fun onSelectedItem(listener: OnSelectedItemCallBack) {
        onSelectedItemCallBack = listener
    }
}