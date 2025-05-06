package com.rio.weatherapp.presentation.extensions

import kotlin.math.roundToInt

fun Float.toTempFormattedString() = "${roundToInt()} °C"