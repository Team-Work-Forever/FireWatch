package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivitySettingsBinding
import com.example.firewatch.presentation.components.dropDown.DefaultDropDrownAdapter
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownAdapter
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownFilter
import com.example.firewatch.presentation.components.dropDown.OnDropDownItemSelected
import com.example.firewatch.presentation.viewModels.SettingsViewModel
import com.example.firewatch.services.locales.Language
import com.example.firewatch.services.store.options.LanguageStore
import com.example.firewatch.shared.helpers.BaseActivity

class Settings : BaseActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    private val letterSizing = arrayOf(
        "Grande",
        "Médio",
        "Pequena"
    )

    companion object {
        fun create(context: Context) {
            val intent = Intent(context, Settings::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val countries = Language.getSupportedLocales().associate {
            Pair(it.code.toLanguageTag(), it.language)
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        val countriesDropDown = binding.countriesDropDown
        val languageAdapter = LanguageDropDownAdapter(this, countries)
        countriesDropDown.setAdapter(languageAdapter)
        countriesDropDown.setFilters(arrayOf(LanguageDropDownFilter(languageAdapter)))

        countriesDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
            override fun onItemSelected(item: String) {
                val language = item.split(" \t ")

                if (language.size != 2) {
                    return
                }

                val foundLanguage = Language.getByLanguage(language[1]) ?: return

                storeController.set(LanguageStore(foundLanguage.code.toLanguageTag()))
                reloadActivity()
            }
        })

        val letterSizeDropDown = binding.letterSize
        letterSizeDropDown.setAdapter(DefaultDropDrownAdapter(this, letterSizing))

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.endSession.setOnClickListener {
            val logoutResult = viewModel.logout()

            if (logoutResult) {
                LoginActivity.new(this)
            }
        }
    }

    private fun reloadActivity() {
        val intent = Intent(this@Settings, Settings::class.java).apply {
            flags = flags or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }
}