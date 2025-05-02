package com.rio.weatherapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rio.weatherapp.data.local.model.CityDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCitiesDao {

    @Query("SELECT * FROM favourite_cities")
    fun getFavouriteCities(): Flow<List<CityDbModel>>

    @Query("SELECT EXISTS (SELECT * FROM favourite_cities WHERE id=:cityId LIMIT 1)")
    fun observeIsFavorite(cityId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourite(city: CityDbModel)

    @Query("DELETE FROM favourite_cities WHERE id=:citiId")
    suspend fun removeFromFavorite(citiId: Int)
}