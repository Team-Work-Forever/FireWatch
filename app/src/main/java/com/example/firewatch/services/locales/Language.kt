package com.example.firewatch.services.locales

import android.content.Context
import java.util.Locale

class LanguageNotDefined() : Exception("Could not apply language to system")

enum class Language(
    val code: Locale,
    val language: String
) {
    English(Locale.forLanguageTag("en-US"), "English"),
    Portugal(Locale.forLanguageTag("pt-PT"), "Portugal"),
    Greece(Locale.forLanguageTag("gr-GR"), "Grécia")
    ;

    companion object {
        private var currentLocale: Language = getCurrentLanguage()

        private fun getCurrentLanguage(): Language {
            val code = Locale.getDefault().toLanguageTag()

            return getByCode(code) ?: English
        }

        fun setLanguage(context: Context, language: Language): Context {
            val locale = language.code
            val configuration = context.resources.configuration

            configuration.setLocale(locale)
            currentLocale = language
            return context.createConfigurationContext(configuration)
        }

        fun getByCode(code: String): Language? {
            return Language.entries.find {
                it.code.toLanguageTag().contains(code)
            }
        }

        fun getByLanguage(language: String): Language? {
            return Language.entries.find {
                it.language == language
            }
        }

        fun getSupportedLocales(): List<Language> {
            return Language.entries.sortedBy {
                it.code.toLanguageTag() != currentLocale.code.toLanguageTag()
            }.toList()
        }

        fun getCodes(): List<String> {
            return Language.entries.map {
                it.code.toLanguageTag()
            }
        }

        fun getLanguage(): List<String> {
            return Language.entries.map {
                it.language
            }
        }
    }
}