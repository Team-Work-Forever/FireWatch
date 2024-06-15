package com.example.firewatch.services.store.options

import android.content.SharedPreferences

abstract class KeyValueStore<TExpectedValue>(
    val key: String
) {
    abstract fun setValue(editor: SharedPreferences.Editor): SharedPreferences.Editor
    abstract fun getValue(editor: SharedPreferences): TExpectedValue?
}
