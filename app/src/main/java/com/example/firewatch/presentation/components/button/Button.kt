package com.example.firewatch.presentation.components.button

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.example.firewatch.R

class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs){
    init {
        attrs?.let {
            val attributes: TypedArray = context.obtainStyledAttributes(
                it, R.styleable.Button, 0, 0
            )

            val title = attributes.getText(R.styleable.Button_title)

            text = title
            setBackgroundColor(resources.getColor(R.color.orange))
            setTextAppearance(R.style.textStyle)
            textSize = 24F
            height = 75

            attributes.recycle()
        }
    }
}