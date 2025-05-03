package com.rio.weatherapp.data.repository

import com.rio.weatherapp.data.mapper.toEntity
import com.rio.weatherapp.data.network.api.ApiService
import com.rio.weatherapp.domain.entity.Forecast
import com.rio.weatherapp.domain.entity.Weather
import com.rio.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {

    override suspend fun getWeather(cityId: Int): Weather =
        apiService.getCurrentWeather("$PREFIX_CITY_ID$cityId").toEntity()

    override suspend fun getForecast(cityId: Int): Forecast =
        apiService.getWeatherForecast("$PREFIX_CITY_ID$cityId").toEntity()

    private companion object {
        private const val PREFIX_CITY_ID = ":id"
    }
}