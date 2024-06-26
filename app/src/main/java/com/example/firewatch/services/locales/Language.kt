package com.example.firewatch.services.locales

import android.content.Context
import com.example.firewatch.R
import java.util.Locale

class LanguageNotDefined() : Exception("Could not apply language to system")

enum class Language(
    val code: Locale,
    val language: Int
) {
    English(Locale.forLanguageTag("en-US"), R.string.english_lang),
    Portugal(Locale.forLanguageTag("pt-PT"), R.string.portuguese_lang),
    Greece(Locale.forLanguageTag("el-GR"), R.string.greece_lang),
    France(Locale.forLanguageTag("fr-FR"), R.string.frenche)
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

        fun getByLanguage(context: Context, language: String): Language? {
            return Language.entries.find {
                context.getString(it.language) == language
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

        fun getLanguage(context: Context): List<String> {
            return Language.entries.map {
                context.getString(it.language)
            }
        }
    }
}