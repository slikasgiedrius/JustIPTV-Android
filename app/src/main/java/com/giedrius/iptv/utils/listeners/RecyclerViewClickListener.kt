package com.giedrius.iptv.utils.listeners

import com.giedrius.iptv.data.model.Channel

interface RecyclerViewClickListener {
    fun onPlaylistClickListener(item: Channel)
}