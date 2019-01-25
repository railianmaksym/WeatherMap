package com.dev.android.railian.weathermap.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.repository.MapsFragmentRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapFragmentViewModel @Inject constructor(
    protected val repository: MapsFragmentRepository
) : ViewModel() {
    private var weatherInfo: LiveData<WeatherInfo> = MutableLiveData<WeatherInfo>()

    fun getWeatherByCoordinates(coordinates: LatLng) {
        GlobalScope.launch(Dispatchers.Main) {
            weatherInfo = repository.getWeatherByCoordinates(coordinates)
        }
    }

    fun getWeatherByCityName(name: String) {
        GlobalScope.launch(Dispatchers.Main) {
            weatherInfo = repository.getWeatherByCityName(name)
        }
    }

    fun weatherInfo(): LiveData<WeatherInfo> {
        return weatherInfo
    }
}