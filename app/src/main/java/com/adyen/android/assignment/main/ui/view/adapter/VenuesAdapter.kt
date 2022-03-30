package com.adyen.android.assignment.main.ui.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.core.domain.entities.ResultModel
import com.adyen.android.assignment.core.extensions.gone
import com.adyen.android.assignment.core.extensions.loadFromUrl
import com.adyen.android.assignment.core.extensions.setUpGridRecyclerView
import com.adyen.android.assignment.databinding.ItemVenueBinding
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class VenuesAdapter(
    val list: List<ResultModel?>,
    val context: Context,
    val onVenueSelectedCallback: (ResultModel) -> Unit?
) : RecyclerView.Adapter<VenuesAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemVenueBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    class ItemViewHolder(val binding: ItemVenueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(
            venue: ResultModel?,
            context: Context,
            onVenueSelectedCallback: (ResultModel) -> Unit?
        ) {
            binding.apply {
                venue?.let {
                    val listDetails =
                        arrayListOf(
                            it.distance.toString().plus("m"),
                            it.timezone,
                            it.location?.locality,
                            it.location?.postcode
                        ).filter { it.isNullOrBlank().not() }
                    itemVenueNameTv.text = it.name
                    it.categories?.isNotEmpty()?.let {bool->
                        if (bool){
                            val imageUrl =
                                "${it.categories.get(0)?.icon?.prefix}${it.categories.get(0)?.icon?.suffix}"
                            itemVenueImageView.loadFromUrl(context, imageUrl, requestImageListener())
                        }
                    }
                    recyclerVenueDetails.setUpGridRecyclerView(
                        context,
                        VenueDetailsAdapter(listDetails) {
                            itemView.performClick()
                        }
                    )
                    itemVenueIdTv.text = "#${adapterPosition.plus(1)}"
                    itemView.setOnClickListener {
                        onVenueSelectedCallback(venue)
                    }
                }

            }
        }

        private fun ItemVenueBinding.requestImageListener() =
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemVenueImageProgress.gone()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            }

    }

    override fun onBindViewHolder(holder: VenuesAdapter.ItemViewHolder, position: Int) {
        val resultModel = list?.get(position)
        holder.bindView(resultModel, context, onVenueSelectedCallback)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}
