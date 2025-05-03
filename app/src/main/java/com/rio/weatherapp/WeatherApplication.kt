package com.rio.weatherapp

import android.app.Application
import com.rio.weatherapp.di.ApplicationComponent
import com.rio.weatherapp.di.DaggerApplicationComponent

class WeatherApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}