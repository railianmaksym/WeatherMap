package com.dev.android.railian.weathermap.data_layer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.android.railian.weathermap.data_layer.pojo.Location

@Dao
interface FavoritesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: Location)

    @Query("DELETE FROM favorites_table")
    fun clearFavorites()

    @Query("SELECT * FROM favorites_table")
    fun getAllFavoriteLocations(): List<Location>

    @Query("DELETE FROM favorites_table WHERE id = :id")
    fun deleteSingleLocationFromFavorite(id: Int)

    @Query("SELECT * FROM favorites_table WHERE id = :id")
    fun findLocationById(id: Int): Location?
}