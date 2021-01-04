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
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.databinding.ItemChannelBinding
import com.giedrius.iptv.utils.listeners.ChannelClickListener

class ChannelsAdapter(
    private var channels: List<Channel>,
    private val context: Context,
    private val channelClickListener: ChannelClickListener
): RecyclerView.Adapter<ChannelsAdapter.ChannelViewHolder>() {

    override fun getItemCount(): Int {
        return channels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            channelClickListener.onChannelClickListener(channels[position])
        }
        holder.binding.ivChannelLogo.setOnClickListener {
            channelClickListener.onFavouriteClickListener(channels[position])
        }
        holder.binding.tvChannelName.text = channels[position].itemName

        bindImage(holder, channels[position])
    }

    fun update(newItems: List<Channel>) {
        this.channels = newItems
        this.notifyDataSetChanged()
    }

    private fun bindImage(holder: ChannelViewHolder, channel: Channel) {
        holder.binding.ivChannelLogo.load(channel.itemLogo) {
            if (channel.itemLogo.isNullOrEmpty()) {
                holder.binding.ivChannelLogo.load(R.drawable.ic_image_placeholder)
            } else {
                holder.binding.ivChannelLogo.load(channel.itemLogo)
            }
            placeholder(R.drawable.ic_image_placeholder)
            error(R.drawable.ic_no_image_placeholder)
        }
    }

    inner class ChannelViewHolder(val binding: ItemChannelBinding):RecyclerView.ViewHolder(binding.root)
}