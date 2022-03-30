package com.adyen.android.assignment.core.data.model

import com.adyen.android.assignment.core.domain.entities.CategoryModel
import com.adyen.android.assignment.core.utils.ResponseDataObject

data class Category(
    val icon: Icon?,
    val id: String?,
    val name: String?,
) : ResponseDataObject<CategoryModel?> {
    override fun toDomain(): CategoryModel = CategoryModel(icon?.toDomain(), id, name)
}
