package com.adyen.android.assignment.core.di

import com.adyen.android.assignment.core.data.repository.PlacesRepositoryImpl
import com.adyen.android.assignment.core.domain.repositories.IPlacesRepository
import com.adyen.android.assignment.core.domain.usecase.PlacesService
import com.adyen.android.assignment.core.domain.usecase.PlacesUseCase
import org.koin.dsl.module

val networkModule = module {

    single<PlacesUseCase> { PlacesService(get()) }
    single { PlacesRepositoryImpl() }
    single<IPlacesRepository> { PlacesRepositoryImpl() }

}
