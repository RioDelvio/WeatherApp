package com.rio.weatherapp.domain.repository

import com.rio.weatherapp.domain.entity.City

interface SearchRepository {

    suspend fun searchCity(query: String): List<City>
}