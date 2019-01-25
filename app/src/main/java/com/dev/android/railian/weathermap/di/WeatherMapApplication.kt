package com.dev.android.railian.weathermap.di

import android.app.Application

class WeatherMapApplication : Application() {

    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .androidModule(AndroidModule(this))
            .networkModule(NetworkModule(this))

            .build()
    }

    companion object {
        private lateinit var appComponent: AppComponent
        @JvmStatic
        fun appComponent(): AppComponent {
            return appComponent
        }
    }

}