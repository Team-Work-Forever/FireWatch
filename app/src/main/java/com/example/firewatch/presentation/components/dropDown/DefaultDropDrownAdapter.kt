package com.example.firewatch.presentation.components.dropDown

import android.content.Context
import android.widget.ArrayAdapter
import com.example.firewatch.R

class DefaultDropDrownAdapter(
    context: Context,
    private val array: Array<String>,
) : ArrayAdapter<String>(context, R.layout.dropdown, array)