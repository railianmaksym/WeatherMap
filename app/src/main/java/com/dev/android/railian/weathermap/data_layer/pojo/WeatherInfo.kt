package com.dev.android.railian.weathermap.data_layer.pojo

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("clouds")
    val clouds: Clouds = Clouds(),
    @SerializedName("coord")
    val coord: Coord = Coord(),
    @SerializedName("dt")
    val requestTime: Int = 0,
    @SerializedName("id")
    val cityId: Int = 0,
    @SerializedName("main")
    val main: Main = Main(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("rain")
    val rain: Rain = Rain(),
    @SerializedName("sys")
    val sys: Sys = Sys(),
    @SerializedName("weather")
    val weather: List<Weather> = listOf(),
    @SerializedName("wind")
    val wind: Wind = Wind()
) {
    data class Sys(
        @SerializedName("country")
        val countryCode: String = "",
        @SerializedName("sunrise")
        val sunriseTime: Int = 0,
        @SerializedName("sunset")
        val sunsetTime: Int = 0
    )

    data class Main(
        @SerializedName("humidity")
        val humidityPercent: Int = 0,
        @SerializedName("pressure")
        val pressure: Double = 0.0,
        @SerializedName("temp")
        val temp: Double = 0.0,
        @SerializedName("temp_max")
        val tempMax: Double = 0.0,
        @SerializedName("temp_min")
        val tempMin: Double = 0.0
    )

    data class Wind(
        @SerializedName("deg")
        val deg: Double = 0.0,
        @SerializedName("speed")
        val speed: Double = 0.0
    )

    data class Rain(
        @SerializedName("3h")
        val lastThreeHoursRainStrength: Double = 0.0
    )

    data class Weather(
        @SerializedName("description")
        val description: String = "",
        @SerializedName("icon")
        val icon: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("main")
        val main: String = ""
    )

    data class Coord(
        @SerializedName("lat")
        val latitude: Double = 0.0,
        @SerializedName("lon")
        val longitude: Double = 0.0
    )

    data class Clouds(
        @SerializedName("all")
        val cloudinessPercent: Int = 0
    )
}