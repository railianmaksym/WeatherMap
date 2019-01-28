package com.dev.android.railian.weathermap.data_layer.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Location(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "Unknown location"
)