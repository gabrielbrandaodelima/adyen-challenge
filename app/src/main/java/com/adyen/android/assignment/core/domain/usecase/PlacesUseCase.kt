package com.adyen.android.assignment.core.domain.usecase

import com.adyen.android.assignment.core.data.repository.PlacesRepositoryImpl
import com.adyen.android.assignment.core.domain.entities.ResponseWrapperModel
import com.adyen.android.assignment.core.domain.repositories.IPlacesRepository

class PlacesService(
        private val repository: PlacesRepositoryImpl
) : PlacesUseCase {
    override suspend fun getVenueRecommendations(query: Map<String, String>): ResponseWrapperModel? {
        return repository.getVenueRecommendations(query)
    }

}

interface PlacesUseCase: IPlacesRepository