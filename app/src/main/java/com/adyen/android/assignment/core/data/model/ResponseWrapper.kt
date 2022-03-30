package com.adyen.android.assignment.core.data.model

import com.adyen.android.assignment.core.domain.entities.ResponseWrapperModel
import com.adyen.android.assignment.core.utils.ResponseDataObject

data class ResponseWrapper(
    val results: List<Result?>?,
):ResponseDataObject<ResponseWrapperModel?> {
    override fun toDomain(): ResponseWrapperModel? {
        return ResponseWrapperModel(results?.map { it?.toDomain() })
    }
}
