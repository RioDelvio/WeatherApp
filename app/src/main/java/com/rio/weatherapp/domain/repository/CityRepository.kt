package com.rio.weatherapp.domain.repository

import com.rio.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    fun favouriteCities(): Flow<List<City>>

    fun observeIsFavourite(cityId: Int): Flow<Boolean>

    suspend fun addToFavorite(city: City)

    suspend fun removeFromFavorite(cityId: Int)
}