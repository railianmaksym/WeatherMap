package com.dev.android.railian.weathermap.di.viewModelInjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.android.railian.weathermap.view_model.MapFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MapFragmentViewModel::class)
    abstract fun bindUserViewModel(mapFragmentViewModel: MapFragmentViewModel): ViewModel

    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}