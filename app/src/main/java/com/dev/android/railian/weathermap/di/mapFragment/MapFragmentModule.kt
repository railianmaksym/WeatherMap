package com.dev.android.railian.weathermap.di.mapFragment

import androidx.lifecycle.ViewModel
import com.dev.android.railian.weathermap.data_layer.WeatherApi
import com.dev.android.railian.weathermap.di.viewModelInjection.ViewModelKey
import com.dev.android.railian.weathermap.repository.MapsFragmentRepository
import com.dev.android.railian.weathermap.view_model.MapFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MapFragmentModule {

    @Provides
    @MapFragmentScope
    fun provideMapFragmentRepository(weatherApi: WeatherApi): MapsFragmentRepository {
        return MapsFragmentRepository(weatherApi)
    }
}