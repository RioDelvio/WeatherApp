package com.rio.weatherapp.domain.repository

import com.rio.weatherapp.domain.entity.Forecast
import com.rio.weatherapp.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast
}