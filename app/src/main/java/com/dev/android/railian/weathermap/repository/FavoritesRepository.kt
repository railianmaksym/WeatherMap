package com.dev.android.railian.weathermap.repository

import com.dev.android.railian.weathermap.data_layer.database.FavoritesDAO
import com.dev.android.railian.weathermap.data_layer.network.WeatherApi
import com.dev.android.railian.weathermap.data_layer.pojo.FavoriteLocationsWeatherResponse
import com.dev.android.railian.weathermap.data_layer.pojo.Location
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.util.Constants
import kotlinx.coroutines.*

class FavoritesRepository(
    private val weatherApi: WeatherApi,
    private val favoritesDAO: FavoritesDAO
) {
    fun insert(location: Location): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            favoritesDAO.insert(location)
        }
    }

    fun clearFavorites(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            favoritesDAO.clearFavorites()
        }
    }

    suspend fun getFavoriteLocations(): List<Location> {
        return withContext(Dispatchers.IO) {
            favoritesDAO.getAllFavoriteLocations()
        }
    }

    fun deleteSingleLocationFromFavorite(id: Int): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            favoritesDAO.deleteSingleLocationFromFavorite(id)
        }
    }

    suspend fun getFavoriteLocationsWeather(ids: String): List<WeatherInfo> {
        val response = weatherApi.getWeatherForCeveralCitiesAsync(
            ids,
            "metric",
            Constants.APP_ID
        ).await()
        return response.weatherList
    }
}