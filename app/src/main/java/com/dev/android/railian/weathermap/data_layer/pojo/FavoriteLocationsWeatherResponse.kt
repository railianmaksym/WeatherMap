package com.dev.android.railian.weathermap.data_layer.pojo

import com.google.gson.annotations.SerializedName

data class FavoriteLocationsWeatherResponse(
    @SerializedName("cnt") val count: Int = 0,
    @SerializedName("list") val weatherList: List<WeatherInfo> = listOf()
)