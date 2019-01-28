package com.dev.android.railian.weathermap.data_layer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.android.railian.weathermap.data_layer.pojo.Location

@Database(entities = [Location::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDAO

    private var instance: FavoritesDatabase? = null

    fun getInstance(context: Context): FavoritesDatabase? {
        if (instance == null) {
            synchronized(FavoritesDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java, "favorites_database"
                ).build()
            }
        }
        return instance
    }
}