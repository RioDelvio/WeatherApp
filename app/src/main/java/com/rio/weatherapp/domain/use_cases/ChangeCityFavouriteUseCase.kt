package com.rio.weatherapp.domain.use_cases

import com.rio.weatherapp.domain.entity.City
import com.rio.weatherapp.domain.repository.CityRepository
import javax.inject.Inject

class ChangeCityFavouriteUseCase @Inject constructor(
    private val repository: CityRepository
) {

    suspend fun addToFavourite(city: City) = repository.addToFavorite(city)

    suspend fun removeFromFavourite(cityId: Int) = repository.removeFromFavorite(cityId)
}