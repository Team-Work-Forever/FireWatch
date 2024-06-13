package com.example.firewatch.services.store

import com.example.firewatch.services.store.options.KeyValueStore

interface StoreController {
    fun <TExpectedValue> set(keyValue: KeyValueStore<TExpectedValue>)
    fun <TExpectedValue : KeyValueStore<*>, TResult> get(clazz: Class<out TExpectedValue>): TResult?
    fun <TExpectedValue : KeyValueStore<*>> remove(clazz: Class<out TExpectedValue>): Boolean
}