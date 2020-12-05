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
import com.giedrius.iptv.utils.listeners.FavouriteClickListener
import kotlinx.android.synthetic.main.item_favourite.view.*

class FavouritesAdapter(
    private var favourites: List<Favourite>,
    private val context: Context,
    private val recyclerViewClickListener: FavouriteClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return favourites.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_favourite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            recyclerViewClickListener.onFavouriteClickListener(favourites[position])
        }
        holder.favouriteChannelName.text = favourites[position].channel.itemName

        bindImage(holder, favourites[position])
    }

    fun update(newItems: List<Favourite>) {
        this.favourites = newItems
        this.notifyDataSetChanged()
    }

    private fun bindImage(holder: ViewHolder, favourite: Favourite) {
        holder.favouriteChannelLogo.load(favourite.channel.itemLogo) {
            if (favourite.channel.itemLogo.isNullOrEmpty()) {
                holder.favouriteChannelLogo.load(R.drawable.ic_image_placeholder)
            } else {
                holder.favouriteChannelLogo.load(favourite.channel.itemLogo)
            }
            placeholder(R.drawable.ic_image_placeholder)
            error(R.drawable.ic_no_image_placeholder)
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val favouriteChannelLogo: ImageView = view.ivFavouriteLogo
    val favouriteChannelName: TextView = view.tvFavouriteName
}