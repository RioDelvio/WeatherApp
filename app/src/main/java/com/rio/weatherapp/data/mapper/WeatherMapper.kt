package com.rio.weatherapp.data.mapper

import android.icu.util.Calendar
import com.rio.weatherapp.data.network.dto.CurrentWeatherDto
import com.rio.weatherapp.data.network.dto.WeatherDto
import com.rio.weatherapp.data.network.dto.WeatherForecastDto
import com.rio.weatherapp.domain.entity.Forecast
import com.rio.weatherapp.domain.entity.Weather
import java.util.Date

fun WeatherDto.toEntity(): Weather = Weather(
    tempC,
    condition.text,
    condition.iconUrl.toCorrectImageUrl(),
    date.toCalendar()
)

fun CurrentWeatherDto.toEntity(): Weather = current.toEntity()

fun WeatherForecastDto.toEntity(): Forecast = Forecast(
    currentWeather = current.toEntity(),
    upcoming = forecast.forecastDay.map {
        val dayWeatherDto = it.day
        Weather(
            tempC = dayWeatherDto.tempC,
            conditionText = dayWeatherDto.condition.text,
            conditionUrl = dayWeatherDto.condition.iconUrl.toCorrectImageUrl(),
            date = it.date.toCalendar()
        )
    }
)

private fun Long.toCalendar(): Calendar = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.toCorrectImageUrl() = "https:$this".replace(
    oldValue = "64x64",
    newValue = "128x128"
)