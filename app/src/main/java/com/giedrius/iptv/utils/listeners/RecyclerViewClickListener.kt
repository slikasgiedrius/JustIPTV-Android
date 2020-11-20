package com.giedrius.iptv.utils.listeners

import com.giedrius.iptv.parser.M3UItem

interface RecyclerViewClickListener {
    fun onPlaylistClickListener(item: M3UItem)
}