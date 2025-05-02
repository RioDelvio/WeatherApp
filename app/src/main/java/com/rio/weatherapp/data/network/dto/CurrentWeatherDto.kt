package com.rio.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("current") val current: WeatherDto
)
