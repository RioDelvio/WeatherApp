package com.rio.weatherapp.di

import android.content.Context
import com.rio.weatherapp.data.local.db.FavoriteCitiesDatabase
import com.rio.weatherapp.data.local.db.FavouriteCitiesDao
import com.rio.weatherapp.data.network.api.ApiFactory
import com.rio.weatherapp.data.network.api.ApiService
import com.rio.weatherapp.data.repository.CityRepositoryImpl
import com.rio.weatherapp.data.repository.SearchRepositoryImpl
import com.rio.weatherapp.data.repository.WeatherRepositoryImpl
import com.rio.weatherapp.domain.repository.CityRepository
import com.rio.weatherapp.domain.repository.SearchRepository
import com.rio.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindCityRepository(impl: CityRepositoryImpl): CityRepository

    @ApplicationScope
    @Binds
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @ApplicationScope
    @Binds
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService

        @ApplicationScope
        @Provides
        fun provideFavoriteCitiesDatabase(context: Context): FavoriteCitiesDatabase =
            FavoriteCitiesDatabase.getInstance(context)

        @ApplicationScope
        @Provides
        fun provideFavouriteCitiesDao(database: FavoriteCitiesDatabase): FavouriteCitiesDao =
            database.favouriteCitiesDao()
    }
}