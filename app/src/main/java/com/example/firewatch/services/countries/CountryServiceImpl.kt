package com.example.firewatch.services.countries

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryServiceImpl : CountryService {
    private const val BASE_URL = "https://country.io/"
    private val client = OkHttpClient.Builder()

    private val retrofit: Retrofit by lazy {
        val gsonOptions = GsonBuilder()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonOptions))
            .client(client.build())
            .build()
    }

    private val countryApi: CountryApi by lazy {
        retrofit.create(CountryApi::class.java)
    }

    override suspend fun getCountries(): Map<String, String> {
        val response = countryApi.getCodes()

        if (!response.isSuccessful) {
            return mapOf()
        }

        val codes = response.body()!!
        val comparator = compareBy<String> { it != "PT" }.thenBy { it }

        return codes.toSortedMap(comparator).map {
            Pair("none-${it.key.lowercase()}", "+${it.value}")
        }.toMap()
    }
}