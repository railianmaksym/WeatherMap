package com.dev.android.railian.weathermap.di

import com.dev.android.railian.weathermap.repository.MapsFragmentRepository
import com.dev.android.railian.weathermap.view_model.MapFragmentViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object MapFragmentModule {
    val instance = module {
        single<MapsFragmentRepository> {
            MapsFragmentRepository(get(), get())
        }

        viewModel {
            MapFragmentViewModel(get())
        }
    }
}