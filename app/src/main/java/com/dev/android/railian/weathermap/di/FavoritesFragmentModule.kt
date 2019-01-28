package com.dev.android.railian.weathermap.di

import com.dev.android.railian.weathermap.repository.FavoritesRepository
import com.dev.android.railian.weathermap.view_model.FavoritesFragmentViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object FavoritesFragmentModule {
    val instance = module {
        single {
            FavoritesRepository(get(), get())
        }

        viewModel {
            FavoritesFragmentViewModel(get())
        }
    }
}