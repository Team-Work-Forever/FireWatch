package com.example.firewatch.services.store.options

import android.content.SharedPreferences

class RefreshTokenStore : KeyValueStore<String> {
    private lateinit var token: String

    companion object {
        const val KEY = "refresh_token"
    }

    constructor() : super(KEY)

    constructor(token: String) : this() {
        this.token = token
    }

    override fun setValue(editor: SharedPreferences.Editor): SharedPreferences.Editor {
        return editor.putString(key, token)
    }

    override fun getValue(editor: SharedPreferences): String? {
        return editor.getString(key, "")
    }
}