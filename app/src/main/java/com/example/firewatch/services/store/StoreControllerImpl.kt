package com.example.firewatch.services.store

import android.content.Context
import com.example.firewatch.services.store.options.KeyValueStore

@Suppress("UNCHECKED_CAST")
class StoreControllerImpl(
    context: Context
) : StoreController {
    private val sharedContext = context.getSharedPreferences(APPLICATION_SHARED_KEY, Context.MODE_PRIVATE)

    companion object {
        private const val APPLICATION_SHARED_KEY = "firewatch"
    }

    private fun <TExpectedValue : KeyValueStore<*>> getKeyValueInstance(clazz: Class<out TExpectedValue>): TExpectedValue? {
        try {
            val keyValueCtors = clazz.constructors

            if (keyValueCtors.isEmpty()) {
                throw Exception("Please provide an constructor without paramters")
            }

            return keyValueCtors[0].newInstance() as TExpectedValue
        } catch (_: Exception) {
            return null
        }
    }

    override fun <TExpectedValue> set(keyValue: KeyValueStore<TExpectedValue>) {
        keyValue.setValue(sharedContext.edit()).apply()
    }

    override fun <TExpectedValue : KeyValueStore<*>, TResult> get(clazz: Class<out TExpectedValue>): TResult? {
        getKeyValueInstance(clazz)?.let {  keyValue ->
            return keyValue.getValue(sharedContext) as TResult
        }

        return null
    }

    override fun <TExpectedValue : KeyValueStore<*>> remove(clazz: Class<out TExpectedValue>): Boolean {
        val keyValue = getKeyValueInstance(clazz)

        keyValue?.let {
            val edit = sharedContext.edit()
            edit.remove(it.key)

            edit.apply()
            return true
        }

        return false
    }
}