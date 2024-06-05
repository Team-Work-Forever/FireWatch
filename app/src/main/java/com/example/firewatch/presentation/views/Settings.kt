package com.example.firewatch.presentation.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivitySettingsBinding
import com.example.firewatch.databinding.DropdownBinding
import com.example.firewatch.presentation.components.dropDown.DefaultDropDrownAdapter
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownAdapter
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Settings : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private val countries = mapOf(
        Pair("pt", "Portugal"),
        Pair("gb", "English")
    )

    private val letterSizing = arrayOf(
        "Grande",
        "Médio",
        "Pequena"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        val countriesDropDown = binding.countriesDropDown
        val languageAdapter = LanguageDropDownAdapter(this, countries)
        countriesDropDown.setAdapter(languageAdapter)
        countriesDropDown.setFilters(arrayOf(LanguageDropDownFilter(languageAdapter)))

        val letterSizeDropDown = binding.letterSize
        letterSizeDropDown.setAdapter(DefaultDropDrownAdapter(this, letterSizing))

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent);
        }
    }
}