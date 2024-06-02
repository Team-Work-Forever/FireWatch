package com.example.firewatch.presentation.components.textField

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.firewatch.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@SuppressLint("ViewConstructor", "MissingInflatedId")
class TextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private var inputLayout: TextInputLayout
    private val inputEdit: TextInputEditText

    var text: CharSequence? = null

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.text_field, this, true)
        inputLayout = layout.findViewById(R.id.textInputLayout)
        inputEdit = layout.findViewById(R.id.textInputEdit)

        attrs?.let {
            val attributes: TypedArray = context.obtainStyledAttributes(
                it, R.styleable.TextField, 0, 0
            )

            val inputTitle = attributes.getText(R.styleable.TextField_inputText)
            val inputType = TextFieldType.get(
                attributes.getInt(
                    R.styleable.TextField_input_type,
                    TextFieldType.NORMAL.id
                )
            )
            text = attributes.getText(R.styleable.TextField_text) ?: ""

            inputEdit.setText(text)
            setType(inputType)
            setInputText(inputTitle ?: "")

            inputEdit.setTextAppearance(R.style.textStyle)
            inputEdit.textSize = 16F

            attributes.recycle()
        }
    }

     private fun setInputText(input: CharSequence) {
        inputLayout.hint = input
    }

     private fun setDefaultTextField() {
        inputEdit.inputType = InputType.TYPE_CLASS_TEXT
        inputLayout.endIconMode = TextInputLayout.END_ICON_NONE
    }

     private fun setPasswordTextField() {
        inputEdit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        inputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
    }

     private fun setEmailTextField() {
        inputEdit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        inputLayout.endIconMode = TextInputLayout.END_ICON_NONE
    }

     fun setType(type: TextFieldType?) {
        when (type) {
            TextFieldType.NORMAL -> setDefaultTextField()
            TextFieldType.PASSWORD -> setPasswordTextField()
            TextFieldType.EMAIL -> setEmailTextField()
            null -> throw Exception("Not Allowed Type")
        }
    }
}
