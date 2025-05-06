package com.rio.weatherapp.presentation.extensions

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

fun Calendar.formattedFullDate(): String =
    SimpleDateFormat("EEEE | d MMM y", Locale.getDefault()).format(time)

fun Calendar.formattedShortDayOfWeek(): String =
    SimpleDateFormat("EEE", Locale.getDefault()).format(time)