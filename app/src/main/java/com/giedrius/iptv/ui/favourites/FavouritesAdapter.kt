package com.giedrius.iptv.ui.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.giedrius.iptv.R
import com.giedrius.iptv.data.model.Favourite
import com.giedrius.iptv.databinding.ItemChannelBinding
import com.giedrius.iptv.databinding.ItemFavouriteBinding
import com.giedrius.iptv.ui.channels.ChannelsAdapter
import com.giedrius.iptv.utils.listeners.FavouriteClickListener

class FavouritesAdapter(
    private var favourites: List<Favourite>,
    private val context: Context,
    private val recyclerViewClickListener: FavouriteClickListener
): RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder>() {

    override fun getItemCount(): Int {
        return favourites.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding = ItemFavouriteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            recyclerViewClickListener.onFavouriteClickListener(favourites[position])
        }
        holder.binding.tvFavouriteName.text = favourites[position].channel.itemName

        bindImage(holder, favourites[position])
    }

    fun update(newItems: List<Favourite>) {
        this.favourites = newItems
        this.notifyDataSetChanged()
    }

    private fun bindImage(holder: FavouriteViewHolder, favourite: Favourite) {
        holder.binding.ivFavouriteLogo.load(favourite.channel.itemLogo) {
            if (favourite.channel.itemLogo.isNullOrEmpty()) {
                holder.binding.ivFavouriteLogo.load(R.drawable.ic_image_placeholder)
            } else {
                holder.binding.ivFavouriteLogo.load(favourite.channel.itemLogo)
            }
            placeholder(R.drawable.ic_image_placeholder)
            error(R.drawable.ic_no_image_placeholder)
        }
    }

    inner class FavouriteViewHolder(val binding: ItemFavouriteBinding):RecyclerView.ViewHolder(binding.root)
}