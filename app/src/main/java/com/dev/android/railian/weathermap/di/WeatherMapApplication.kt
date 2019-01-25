package com.dev.android.railian.weathermap.di

import android.app.Application
import org.koin.android.ext.android.startKoin

class WeatherMapApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(NetworkModule.instance, MapFragmentModule.instance))
    }
}