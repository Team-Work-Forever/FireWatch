package com.example.firewatch.presentation.adapters.dropdown

import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentActivity
import com.example.firewatch.R

class LanguageAdapter<T>(
    activity: FragmentActivity,
    array: Array<T>
) : ArrayAdapter<T>(activity, R.layout.dropdown, array)