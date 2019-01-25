package com.dev.android.railian.weathermap.util

object Constants {
    val BASE_URL = "http://api.openweathermap.org"
    const val APP_ID = "9f39aeac50811bd3d3aab27cb635f4c8"

    enum class WindDirections(val displayString: String) {
        NORD("Nord"),
        NORDEAST("NordEast"),
        EAST("East"),
        SOUTHEAST("SouthEast"),
        SOUTH("South"),
        SOUTHWEST("SouthWest"),
        WEST("West"),
        NORDWEST("NordWest")
    }
}