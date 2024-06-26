package com.example.firewatch.services.http.interceptiors

import com.example.firewatch.services.locales.Language
import com.example.firewatch.services.store.StoreController
import com.example.firewatch.services.store.options.LanguageStore
import okhttp3.Interceptor
import okhttp3.Response

class AcceptLanguageInterceptor(
    val storeController: StoreController
) : Interceptor {
    companion object {
        private const val ACCEPT_LANGUAGE_HEADER = "Accept-Language"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val interlanguage = storeController.get<LanguageStore, String>(LanguageStore::class.java)
            ?: return chain.proceed(request.build())

        val language = Language.getByCode(interlanguage)
            ?: return chain.proceed(request.build())

        request.addHeader(ACCEPT_LANGUAGE_HEADER, language.code.toLanguageTag())
        return chain.proceed(request.build())
    }
}