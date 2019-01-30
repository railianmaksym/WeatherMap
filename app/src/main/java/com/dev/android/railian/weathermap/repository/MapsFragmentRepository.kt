package com.dev.android.railian.weathermap.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.android.railian.weathermap.dataLayer.database.FavoritesDAO
import com.dev.android.railian.weathermap.dataLayer.network.WeatherApi
import com.dev.android.railian.weathermap.dataLayer.pojo.Location
import com.dev.android.railian.weathermap.dataLayer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.util.Constants
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MapsFragmentRepository(
    private val weatherApi: WeatherApi,
    private val favoritesDAO: FavoritesDAO
) {
    suspend fun getWeatherByCoordinates(coordinates: LatLng): WeatherInfo {
        return weatherApi.getWeatherByCoordinatesAsync(
            coordinates.latitude.toString(),
            coordinates.longitude.toString(),
            "metric",
            Constants.APP_ID
        ).await()
    }

    suspend fun getWeatherByCityName(name: String): LiveData<WeatherInfo> {
        val weatherInfo = weatherApi.getWeatherByCityNameAsync(
            name,
            "metric",
            Constants.APP_ID
        ).await()

        val liveData = MutableLiveData<WeatherInfo>()
        liveData.value = weatherInfo

        return liveData
    }

    fun addLocationToFavorites(location: Location): Job {
        return GlobalScope.launch {
            favoritesDAO.insert(location)
        }
    }

    fun deleteSingleLocationFromFavorite(id: Int): Job {
        return GlobalScope.launch {
            favoritesDAO.deleteSingleLocationFromFavorite(id)
        }
    }

    fun getFavoriteStatus(id: Int): Boolean {
        var location: Location? = null
        GlobalScope.launch {
            location = favoritesDAO.findLocationById(id)
        }
        return location != null
    }
}