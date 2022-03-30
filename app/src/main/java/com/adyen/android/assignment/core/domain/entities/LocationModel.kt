package com.adyen.android.assignment.core.domain.entities

data class LocationModel(
    val address: String?,
    val country: String?,
    val locality: String?,
    val neighbourhood: List<String?>?,
    val postcode: String?,
    val region: String?,
)
