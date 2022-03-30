package com.adyen.android.assignment.core.data.model

import com.adyen.android.assignment.core.domain.entities.GeoCodeModel
import com.adyen.android.assignment.core.utils.ResponseDataObject

data class GeoCode(
    val main: Main?
) : ResponseDataObject<GeoCodeModel?> {
    override fun toDomain() = GeoCodeModel(main?.toDomain())
}

