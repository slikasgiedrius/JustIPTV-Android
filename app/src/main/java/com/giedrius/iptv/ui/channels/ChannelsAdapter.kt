package com.giedrius.iptv.ui.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giedrius.iptv.R
import com.giedrius.iptv.parser.M3UItem
import com.giedrius.iptv.utils.listeners.RecyclerViewClickListener
import kotlinx.android.synthetic.main.item_channel.view.*

class ChannelsAdapter(
    private val items: ArrayList<M3UItem>,
    private val context: Context,
    private val recyclerViewClickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_channel, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            recyclerViewClickListener.onPlaylistClickListener(items[position])
        }
        holder.text.text = items[position].itemUrl
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val text: TextView = view.tv_animal_type
}