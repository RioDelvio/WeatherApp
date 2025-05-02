package com.rio.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("temp_c") val tempC: Float,
    @SerializedName("condition") val condition: ConditionDto,
    @SerializedName("last_updated_epoch") val date: Long,
)
