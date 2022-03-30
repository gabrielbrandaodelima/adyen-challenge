package com.adyen.android.assignment.core.domain.entities


data class ResultModel(
    val categories: List<CategoryModel?>?,
    val distance: Int?,
    val geocode: GeoCodeModel?,
    val location: LocationModel?,
    val name: String?,
    val timezone: String?,
)