package com.adyen.android.assignment.core.domain.repositories

import com.adyen.android.assignment.core.domain.entities.ResponseWrapperModel

interface IPlacesRepository {
    suspend fun getVenueRecommendations(query: Map<String, String>): ResponseWrapperModel?
}
