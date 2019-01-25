package com.dev.android.railian.weathermap.data_layer

import androidx.lifecycle.LiveData
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather")
    fun getWeatherByCoordinatesAsync(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ):  Deferred<LiveData<WeatherInfo>>

    @GET("/data/2.5/weather")
    fun getWeatherByCityNameAsync(
        @Query("q") q: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Deferred<LiveData<WeatherInfo>>
}