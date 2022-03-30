package com.adyen.android.assignment.core.utils

interface ResponseDataObject<out DomainEntity: Any?> {
    fun toDomain(): DomainEntity
}