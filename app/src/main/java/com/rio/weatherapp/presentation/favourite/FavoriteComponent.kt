package com.rio.weatherapp.presentation.favourite

import com.rio.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavoriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun clickSearch()

    fun clickAddToFavourite()

    fun clickOnCity(city: City)
}