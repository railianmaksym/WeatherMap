package com.dev.android.railian.weathermap.dataLayer.network

import com.dev.android.railian.weathermap.dataLayer.pojo.FavoriteLocationsWeatherResponse
import com.dev.android.railian.weathermap.dataLayer.pojo.WeatherInfo
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
    ): Deferred<WeatherInfo>

    @GET("/data/2.5/weather")
    fun getWeatherByCityNameAsync(
        @Query("q") q: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Deferred<WeatherInfo>

    @GET("/data/2.5/group")
    fun getWeatherForCeveralCitiesAsync(
        @Query("id") cityIDs: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Deferred<FavoriteLocationsWeatherResponse>
}