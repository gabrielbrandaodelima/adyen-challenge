package com.adyen.android.assignment.core.extensions

import android.content.Context
import android.view.View.OVER_SCROLL_NEVER
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView?.setUpRecyclerView(
        context: Context?,
        adapter: RecyclerView.Adapter<*>,
        adapterSetCallback: (rv: RecyclerView) -> Unit = {},
        orientation: Int = RecyclerView.VERTICAL
) {
    this?.apply {
        overScrollMode = OVER_SCROLL_NEVER
        setHasFixedSize(true)
        setItemViewCacheSize(20)
        context?.let {
            layoutManager = LinearLayoutManager((it), orientation, false)
        }
        this.adapter = adapter
        adapterSetCallback(this)
    }
}

fun RecyclerView?.setUpGridRecyclerView(
        context: Context?,
        adapter: RecyclerView.Adapter<*>,
        spanCount: Int = 2,
        adapterSetCallback: (rv: RecyclerView) -> Unit = {},
        orientation: Int = RecyclerView.VERTICAL
) {
    this?.apply {
        setHasFixedSize(true)
        setItemViewCacheSize(20)
        context?.let {
            layoutManager = GridLayoutManager(it, spanCount, orientation, false)
        }
        this.adapter = adapter
        adapterSetCallback(this)
    }
}