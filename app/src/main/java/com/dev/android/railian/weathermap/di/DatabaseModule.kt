package com.dev.android.railian.weathermap.di

import androidx.room.Room
import com.dev.android.railian.weathermap.dataLayer.database.FavoritesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

object DatabaseModule {
    val instance = module {
        single {
            val database = Room.databaseBuilder(
                androidContext(),
                FavoritesDatabase::class.java,
                "favorites_database"
            ).build()
            database.favoritesDao()
        }
    }
}