package com.rio.weatherapp.data.repository

import com.rio.weatherapp.data.local.db.FavouriteCitiesDao
import com.rio.weatherapp.data.mapper.toCityDbModel
import com.rio.weatherapp.data.mapper.toEntities
import com.rio.weatherapp.domain.entity.City
import com.rio.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val favoriteCitiesDao: FavouriteCitiesDao
) : CityRepository {

    override fun favouriteCities(): Flow<List<City>> = favoriteCitiesDao.getFavouriteCities()
        .map { it.toEntities() }

    override fun observeIsFavourite(cityId: Int): Flow<Boolean> = favoriteCitiesDao.observeIsFavorite(cityId)

    override suspend fun addToFavorite(city: City) = favoriteCitiesDao.addToFavourite(city.toCityDbModel())

    override suspend fun removeFromFavorite(cityId: Int) = favoriteCitiesDao.removeFromFavorite(cityId)
}