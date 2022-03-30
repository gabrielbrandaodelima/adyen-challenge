package com.adyen.android.assignment.core.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.adyen.android.assignment.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

fun ImageView.loadFromUrl(context: Context, url: String?, requestListener: RequestListener<Drawable>) =
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_launcher_foreground)
        .listener(requestListener)
        .into(this);