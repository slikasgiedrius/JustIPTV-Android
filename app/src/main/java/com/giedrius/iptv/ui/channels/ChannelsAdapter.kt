package com.giedrius.iptv.ui.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giedrius.iptv.R
import com.giedrius.iptv.data.model.parser.M3UItem
import com.giedrius.iptv.utils.listeners.RecyclerViewClickListener
import kotlinx.android.synthetic.main.item_channel.view.*

class ChannelsAdapter(
    private var items: ArrayList<M3UItem>,
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
        holder.name.text = items[position].itemName
    }

    fun update(newItems:ArrayList<M3UItem>){
        this.items = newItems
        this.notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.tv_name
}