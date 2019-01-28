package com.dev.android.railian.weathermap.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.android.railian.weathermap.data_layer.pojo.Location
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.repository.MapsFragmentRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MapFragmentViewModel(private val repository: MapsFragmentRepository) : ViewModel() {
    private val weatherInfo: MutableLiveData<WeatherInfo> = MutableLiveData()
    private val successAddLocationToFavorites: MutableLiveData<Boolean> = MutableLiveData()
    private val successDeleteLocation: MutableLiveData<Boolean> = MutableLiveData()

    fun getWeatherByCoordinates(coordinates: LatLng) {
        GlobalScope.launch(Dispatchers.Main) {
            weatherInfo.value = repository.getWeatherByCoordinates(coordinates)
        }
    }

    /* fun getWeatherByCityName(name: String) {
         GlobalScope.launch(Dispatchers.Main) {
             weatherInfo = repository.getWeatherByCityName(name)
         }
     }*/

    fun addLocationToFavorites(location: Location) {
        GlobalScope.launch(Dispatchers.Main) {
            repository.addLocationToFavorites(location)
            successAddLocationToFavorites.value = true
        }
    }

    fun deleteSingleLocationFromFavorite(id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            repository.deleteSingleLocationFromFavorite(id)
            successDeleteLocation.value = true
        }
    }

    fun weatherInfo(): LiveData<WeatherInfo> {
        return weatherInfo
    }

    fun getFavoriteStatus(id: Int): Boolean {
        return repository.getFavoriteStatus(id)
    }

    fun successAddLocationToFavorites():MutableLiveData<Boolean>{
        return successAddLocationToFavorites
    }
    fun successDeleteLocation():MutableLiveData<Boolean>{
        return successDeleteLocation
    }
}