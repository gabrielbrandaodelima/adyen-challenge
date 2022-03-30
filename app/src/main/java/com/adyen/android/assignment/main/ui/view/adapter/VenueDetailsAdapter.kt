package com.adyen.android.assignment.main.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.core.extensions.gone
import com.adyen.android.assignment.databinding.ItemVenueDetailBinding

class VenueDetailsAdapter(
    val list: List<String?>?,
    val itemClickCallback: (() -> Boolean)? = null
) : RecyclerView.Adapter<VenueDetailsAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemVenueDetailBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    class ItemViewHolder(val binding: ItemVenueDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(detail: String?, itemClickCallback: (() -> Boolean)?) {
            binding.apply {
                itemVenueType1Tv.apply {
                    if (detail.isNullOrBlank()) gone() else text = detail
                }
                itemView.setOnClickListener {
                    itemClickCallback?.invoke()
                }

            }
        }

    }

    override fun onBindViewHolder(holder: VenueDetailsAdapter.ItemViewHolder, position: Int) {
        val detail = list?.get(position)
        holder.bindView(detail,itemClickCallback)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}
