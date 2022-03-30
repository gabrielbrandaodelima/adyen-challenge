package com.adyen.android.assignment.core.extensions

import android.content.Context
import android.text.Spanned
import android.widget.Toast
import androidx.core.text.HtmlCompat

fun String.Companion.empty() = ""

fun String?.fromHtml(): Spanned {
    return HtmlCompat.fromHtml(this ?: String.empty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun String.toToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.toToastLong(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}