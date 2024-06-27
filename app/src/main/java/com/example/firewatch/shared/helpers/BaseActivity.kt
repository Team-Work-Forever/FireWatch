package com.example.firewatch.shared.helpers

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.example.firewatch.config.DependencyModule
import com.example.firewatch.services.locales.Language
import com.example.firewatch.services.locales.LanguageNotDefined
import com.example.firewatch.services.store.StoreController
import com.example.firewatch.services.store.options.LanguageStore
import com.example.firewatch.shared.utils.TranslateUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var storeController: StoreController

    private fun setUpLocale(context: Context?): Context? {
        context?.let {
            storeController = DependencyModule.provideStoreController(context)

            val code = storeController.get<LanguageStore, String>(LanguageStore::class.java)
                ?: return null

            val language = Language.getByCode(code) ?: throw LanguageNotDefined()
            return Language.setLanguage(context, language)
        }

        return null
    }

    override fun onRestart() {
        super.onRestart()

        startActivity(intent)
        finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        val context = setUpLocale(newBase)

        TranslateUtil.context = context
        super.attachBaseContext(context)
    }
}