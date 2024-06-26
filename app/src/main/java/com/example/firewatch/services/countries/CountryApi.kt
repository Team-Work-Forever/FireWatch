package com.example.firewatch.services.countries

import retrofit2.Response
import retrofit2.http.GET

data class CountryCodes(
    val codes: Map<String, String>
)

interface CountryApi {
    @GET("phone.json")
    suspend fun getCodes(): Response<Map<String, String>>
}