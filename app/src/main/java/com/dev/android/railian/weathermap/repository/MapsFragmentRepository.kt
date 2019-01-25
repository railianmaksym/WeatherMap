package com.dev.android.railian.weathermap.repository

import androidx.lifecycle.LiveData
import com.dev.android.railian.weathermap.data_layer.WeatherApi
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.util.Constants
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class MapsFragmentRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun getWeatherByCoordinates(coordinates: LatLng): LiveData<WeatherInfo> {
        return weatherApi.getWeatherByCoordinatesAsync(
            coordinates.latitude.toString(),
            coordinates.longitude.toString(),
            "metric",
            Constants.APP_ID
        ).await()
    }

    suspend fun getWeatherByCityName(name: String): LiveData<WeatherInfo> {
        return weatherApi.getWeatherByCityNameAsync(
            name,
            "metric",
            Constants.APP_ID
        ).await()
    }
}