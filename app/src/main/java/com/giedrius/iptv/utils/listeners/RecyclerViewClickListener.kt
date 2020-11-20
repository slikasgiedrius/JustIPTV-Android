package com.giedrius.iptv.utils.listeners

import com.giedrius.iptv.data.model.parser.M3UItem

interface RecyclerViewClickListener {
    fun onPlaylistClickListener(item: M3UItem)
}