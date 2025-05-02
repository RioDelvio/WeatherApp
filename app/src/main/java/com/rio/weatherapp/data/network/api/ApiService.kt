package com.rio.weatherapp.data.network.api

import com.rio.weatherapp.data.network.dto.CityDto
import com.rio.weatherapp.data.network.dto.CurrentWeatherDto
import com.rio.weatherapp.data.network.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") query: String
    ): CurrentWeatherDto

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("q") query: String,
        @Query("days") daysCount: Int = 4
    ): WeatherForecastDto

    @GET("search.json")
    suspend fun searchCity(
        @Query("q") query: String
    ): List<CityDto>

}