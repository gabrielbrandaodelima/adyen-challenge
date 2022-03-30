package com.adyen.android.assignment.core.utils

interface RequestDataObject<out RequestEntity: Any?> {
    fun toRequest(): RequestEntity
}