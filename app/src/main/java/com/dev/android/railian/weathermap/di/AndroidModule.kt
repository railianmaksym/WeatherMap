package com.dev.android.railian.weathermap.di

import android.content.Context
import dagger.Module
import javax.inject.Singleton
import dagger.Provides


@Module
class AndroidModule(private val application: WeatherMapApplication) {
    @Provides
    @Singleton
    fun providesContext(): Context {
        return application
    }
}