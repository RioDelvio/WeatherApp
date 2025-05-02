package com.rio.weatherapp.domain.use_cases

import com.rio.weatherapp.domain.repository.CityRepository
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val repository: CityRepository
){

    operator fun invoke() = repository.favouriteCities()
}