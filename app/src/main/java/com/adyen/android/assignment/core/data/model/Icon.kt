package com.adyen.android.assignment.core.data.model

import com.adyen.android.assignment.core.domain.entities.IconModel
import com.adyen.android.assignment.core.utils.ResponseDataObject

data class Icon(
    val prefix: String?,
    val suffix: String?
) : ResponseDataObject<IconModel> {
    override fun toDomain(): IconModel {
        return IconModel(prefix, suffix)
    }
}
