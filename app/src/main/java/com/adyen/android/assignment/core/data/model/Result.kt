package com.adyen.android.assignment.core.data.model

import com.adyen.android.assignment.core.domain.entities.ResultModel
import com.adyen.android.assignment.core.utils.ResponseDataObject

data class Result(
    val categories: List<Category?>?,
    val distance: Int?,
    val geocode: GeoCode?,
    val location: Location?,
    val name: String?,
    val timezone: String?,
) : ResponseDataObject<ResultModel?> {
    override fun toDomain() = ResultModel(
        categories?.map { it?.toDomain() },
        distance,
        geocode?.toDomain(),
        location?.toDomain(),
        name,
        timezone
    )
}