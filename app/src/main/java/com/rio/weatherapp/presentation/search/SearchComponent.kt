package com.rio.weatherapp.presentation.search

import com.rio.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model: StateFlow<SearchStore.State>

    fun clickBack()

    fun clickSearch()

    fun clickOnCity(city: City)

    fun changeSearchQuery(query: String)
}