package com.example.firewatch.shared.helpers

import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import com.example.firewatch.config.DependencyModule
import com.example.firewatch.services.locales.Language
import com.example.firewatch.services.locales.LanguageNotDefined
import com.example.firewatch.services.store.StoreController
import com.example.firewatch.services.store.options.LanguageStore
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
abstract class BaseFragment : Fragment() {
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
}