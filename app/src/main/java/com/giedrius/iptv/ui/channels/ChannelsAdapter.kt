package com.giedrius.iptv.ui.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.giedrius.iptv.R
import com.giedrius.iptv.data.parser.NewM3UItem
import com.giedrius.iptv.utils.listeners.RecyclerViewClickListener
import kotlinx.android.synthetic.main.item_channel.view.*

class ChannelsAdapter(
    private var channels: ArrayList<NewM3UItem>,
    private val context: Context,
    private val recyclerViewClickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return channels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_channel, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            recyclerViewClickListener.onPlaylistClickListener(channels[position])
        }
        holder.channelName.text = channels[position].itemName
        holder.channelLogo.load(channels[position].itemLogo)
    }

    fun update(newItems: ArrayList<NewM3UItem>) {
        this.channels = newItems
        this.notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val channelLogo: ImageView = view.ivChannelLogo
    val channelName: TextView = view.tvChannelName
}