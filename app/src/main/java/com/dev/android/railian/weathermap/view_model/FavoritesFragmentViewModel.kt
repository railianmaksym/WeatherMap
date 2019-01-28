package com.dev.android.railian.weathermap.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.android.railian.weathermap.data_layer.network.WeatherApi
import com.dev.android.railian.weathermap.data_layer.pojo.Location
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.repository.FavoritesRepository
import kotlinx.coroutines.*

class FavoritesFragmentViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val favoriteLocations: MutableLiveData<List<WeatherInfo>> = MutableLiveData()
    private val successDeleteLocations: MutableLiveData<Boolean> = MutableLiveData()
    private val successDeleteLocation: MutableLiveData<Boolean> = MutableLiveData()
    private val successBackupLocation: MutableLiveData<Boolean> = MutableLiveData()
    private val favoritesIsEmpty: MutableLiveData<Boolean> = MutableLiveData()

    fun backupLocation(location: Location) {
        GlobalScope.launch(Dispatchers.Main) {
            favoritesRepository.insert(location)
            successBackupLocation.value = true
        }
    }

    fun clearFavorites() {
        GlobalScope.launch(Dispatchers.Main) {
            favoritesRepository.clearFavorites()
            successDeleteLocations.value = true
        }
    }

    fun getFavoriteLocations() {
        GlobalScope.launch(Dispatchers.IO) {
            val locations = favoritesRepository.getFavoriteLocations().map { it.id }.joinToString(separator = ",")
            GlobalScope.launch(Dispatchers.Main) {
                if (locations.isNotEmpty()) {
                    favoriteLocations.value = favoritesRepository.getFavoriteLocationsWeather(locations)
                } else {
                    favoritesIsEmpty.value = true
                }
            }
        }
    }

    fun deleteSingleLocationFromFavorite(id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            favoritesRepository.deleteSingleLocationFromFavorite(id)
            successDeleteLocation.value = true
        }
    }

    fun favoriteLocations(): MutableLiveData<List<WeatherInfo>> {
        return favoriteLocations
    }

    fun successDeleteLocations(): MutableLiveData<Boolean> {
        return successDeleteLocations
    }

    fun successDeleteLocation(): MutableLiveData<Boolean> {
        return successDeleteLocation
    }

    fun successBackupLocation(): MutableLiveData<Boolean> {
        return successBackupLocation
    }

    fun favoritesIsEmpty(): MutableLiveData<Boolean> {
        return favoritesIsEmpty
    }
}