package com.dev.android.railian.weathermap.di.mapFragment

import com.dev.android.railian.weathermap.view.MapFragment
import dagger.Subcomponent

@Subcomponent(modules = [MapFragmentModule::class])
@MapFragmentScope
interface MapFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun mapFragmentModule(mapFragmentModule: MapFragmentModule): MapFragmentComponent.Builder
        fun build(): MapFragmentComponent
    }

    fun inject(mapFragment: MapFragment)
}