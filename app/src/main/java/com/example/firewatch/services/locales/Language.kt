package com.example.firewatch.services.locales

import android.content.Context
import java.util.Locale

class LanguageNotDefined() : Exception("Could not apply language to system")

enum class Language(val code: String, val language: String) {
    English("en", "English"),
    Portugal("pt", "Portugal"),
    Greece("gr", "Grécia")
    ;

    companion object {
        private var currentLocale: Language = getCurrentLanguage()

        private fun getCurrentLanguage(): Language {
            val code = Locale.getDefault().language;

            return getByCode(code) ?: throw LanguageNotDefined()
        }

        fun setLanguage(context: Context, language: Language): Context {
            val locale = Locale(language.code)
            val configuration = context.resources.configuration

            configuration.setLocale(locale)
            currentLocale = language
            return context.createConfigurationContext(configuration)
        }

        fun getByCode(code: String): Language? {
            return Language.entries.find {
                it.code.contains(code)
            }
        }

        fun getByLanguage(language: String): Language? {
            return Language.entries.find {
                it.language == language
            }
        }

        fun getSupportedLocales(): List<Language> {
            return Language.entries.sortedBy {
                it.code != currentLocale.code
            }.toList()
        }

        fun getCodes(): List<String> {
            return Language.entries.map {
                it.code
            }
        }

        fun getLanguage(): List<String> {
            return Language.entries.map {
                it.language
            }
        }
    }
}