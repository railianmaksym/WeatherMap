package com.dev.android.railian.weathermap.di

import com.dev.android.railian.weathermap.di.mapFragment.MapFragmentComponent
import com.dev.android.railian.weathermap.di.viewModelInjection.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AndroidModule::class, NetworkModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun mapComponentBuilder(): MapFragmentComponent.Builder
}