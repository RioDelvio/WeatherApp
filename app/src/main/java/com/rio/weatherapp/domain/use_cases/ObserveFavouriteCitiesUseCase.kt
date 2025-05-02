package com.rio.weatherapp.domain.use_cases

import com.rio.weatherapp.domain.repository.CityRepository
import javax.inject.Inject

class ObserveFavouriteCitiesUseCase @Inject constructor(
    private val repository: CityRepository
){

    operator fun invoke(cityId: Int) = repository.observeIsFavourite(cityId)
}