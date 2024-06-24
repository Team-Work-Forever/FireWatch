package com.example.firewatch.services.store.options

import android.content.SharedPreferences

class LanguageStore: KeyValueStore<String> {
    private lateinit var code: String

    companion object {
        const val KEY = "language-code"
    }

    constructor() : super(LanguageStore.KEY)

    constructor(code: String) : this() {
        this.code = code
    }

    override fun setValue(editor: SharedPreferences.Editor): SharedPreferences.Editor {
        return editor.putString(key, code)
    }

    override fun getValue(editor: SharedPreferences): String? {
        return editor.getString(key, "")
    }
}