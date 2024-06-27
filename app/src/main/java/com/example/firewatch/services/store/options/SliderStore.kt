package com.example.firewatch.services.store.options

import android.content.SharedPreferences

class SliderStore : KeyValueStore<Boolean> {
    private var value: Boolean = false

    companion object {
        const val KEY = "slider"
    }

    constructor() : super(KEY)

    constructor(value: Boolean) : this() {
        this.value = value
    }

    override fun setValue(editor: SharedPreferences.Editor): SharedPreferences.Editor {
        return editor.putBoolean(key, value)
    }

    override fun getValue(editor: SharedPreferences): Boolean {
        return editor.getBoolean(key, false)
    }
}