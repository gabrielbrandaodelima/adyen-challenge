package com.adyen.android.assignment.core.data.model

import com.adyen.android.assignment.core.domain.entities.MainModel
import com.adyen.android.assignment.core.utils.ResponseDataObject

data class Main(
    val latitude: Double?,
    val longitude: Double?,
) : ResponseDataObject<MainModel?> {
    override fun toDomain() = MainModel(latitude, longitude)
}