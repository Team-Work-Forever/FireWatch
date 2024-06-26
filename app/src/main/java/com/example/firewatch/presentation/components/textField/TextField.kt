package com.example.firewatch.presentation.components.textField

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.firewatch.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private var inputLayout: TextInputLayout
    private val inputEdit: TextInputEditText
    var isRequired: Boolean = false
    private var isPasswordActive = false

    var text: CharSequence? = null
    var error: CharSequence?
        get() = inputLayout.error
        set(value) {
            inputLayout.error = value
        }

    var showPassword: Boolean
        get() = isPasswordActive
        set(value) {
            if (value) {
                inputEdit.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                inputEdit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            inputEdit.typeface = resources.getFont(R.font.londrina_solid)
            isPasswordActive = value
        }

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
            isRequired = attributes.getBoolean(R.styleable.TextField_is_required, false)
            showPassword = attributes.getBoolean(R.styleable.TextField_showPassword, false)

            inputEdit.setText(text)
            inputLayout.error = attributes.getText(R.styleable.TextField_error) ?: ""

            setType(inputType)
            setInputText(inputTitle ?: "")
            updateHelperText(isRequired)

            inputEdit.setTextAppearance(R.style.textStyle)
            inputEdit.textSize = 16F

            attributes.recycle()
        }
    }

    fun setInputText(input: CharSequence) {
        inputLayout.hint = input
    }

    fun updateHelperText(isRequired: Boolean) {
        if (text == null) return

        if (text!!.isNotEmpty() || !isRequired) {
            inputLayout.helperText = ""

            return
        }

        inputLayout.helperText = "Required*"
    }

    fun setText(input: String?) {
        inputEdit.setText(input)
        TextFieldBindingAdapters.setText(this, input)
    }
     private fun setDefaultTextField() {
        inputEdit.inputType = InputType.TYPE_CLASS_TEXT
        inputLayout.endIconMode = TextInputLayout.END_ICON_NONE
    }

     private fun setPasswordTextField() {}

     private fun setEmailTextField() {
        inputEdit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        inputLayout.endIconMode = TextInputLayout.END_ICON_NONE
    }

    private fun setNumber() {
        inputEdit.inputType = InputType.TYPE_CLASS_NUMBER
    }

     fun setType(type: TextFieldType?) {
        when (type) {
            TextFieldType.NORMAL -> setDefaultTextField()
            TextFieldType.PASSWORD -> setPasswordTextField()
            TextFieldType.EMAIL -> setEmailTextField()
            TextFieldType.NUMBER -> setNumber()
            null -> throw Exception("Not Allowed Type")
        }
    }
}
