package com.dev.android.railian.weathermap.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.android.railian.weathermap.data_layer.WeatherApi
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.util.Constants
import com.google.android.gms.maps.model.LatLng

class MapsFragmentRepository(
    private val weatherApi: WeatherApi
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
}