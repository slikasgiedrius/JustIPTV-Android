package com.giedrius.iptv.utils.listeners

import com.giedrius.iptv.data.parser.NewM3UItem

interface RecyclerViewClickListener {
    fun onPlaylistClickListener(item: NewM3UItem)
}