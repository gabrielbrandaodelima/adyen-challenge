package com.adyen.android.assignment.core.data.model

import com.adyen.android.assignment.core.domain.entities.LocationModel
import com.adyen.android.assignment.core.utils.ResponseDataObject

data class Location(
    val address: String?,
    val country: String?,
    val locality: String?,
    val neighbourhood: List<String?>?,
    val postcode: String?,
    val region: String?,
):ResponseDataObject<LocationModel> {
    override fun toDomain(): LocationModel {
        return LocationModel(address, country, locality, neighbourhood, postcode, region)
    }
}
