package com.adyen.android.assignment.core.di

import com.adyen.android.assignment.main.viewmodel.PlacesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelsModule = module {
    viewModel { PlacesViewModel(get()) }
}
