package com.example.firewatch.services.countries

interface CountryService {
    suspend fun getCountries(): Map<String, String>
}