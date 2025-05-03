package com.rio.weatherapp.data.repository

import com.rio.weatherapp.data.mapper.toEntity
import com.rio.weatherapp.data.network.api.ApiService
import com.rio.weatherapp.domain.entity.City
import com.rio.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {

    override suspend fun searchCity(query: String): List<City> = apiService.searchCity(query).map {
        it.toEntity()
    }
}