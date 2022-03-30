package com.adyen.android.assignment.core.data.repository

import com.adyen.android.assignment.core.data.api.PlacesServiceApi
import com.adyen.android.assignment.core.domain.entities.ResponseWrapperModel
import com.adyen.android.assignment.core.domain.repositories.IPlacesRepository

class PlacesRepositoryImpl() : GenericRepository(), IPlacesRepository {
    override suspend fun getVenueRecommendations(query: Map<String, String>): ResponseWrapperModel? {
        return apiCall {
            PlacesServiceApi.instance.getVenueRecommendations(query)
        }?.toDomain()
    }


}